#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:18091}"
IMAGE_NAME="${IMAGE_NAME:-day26-device-monitoring:latest}"
NAMESPACE="${NAMESPACE:-day26-platform}"
PORT_FORWARD_LOG="${PORT_FORWARD_LOG:-/tmp/day26-port-forward.log}"
PORT_FORWARD_PID=""

cleanup() {
  cd "${APP_DIR}"
  if [[ -n "${PORT_FORWARD_PID}" ]]; then
    kill "${PORT_FORWARD_PID}" >/dev/null 2>&1 || true
    wait "${PORT_FORWARD_PID}" >/dev/null 2>&1 || true
  fi
  kubectl delete namespace "${NAMESPACE}" --ignore-not-found >/dev/null 2>&1 || true
}
trap cleanup EXIT

cd "${APP_DIR}"

echo "[1/8] Running Maven clean test"
mvn -q clean test

echo "[2/8] Building Docker image"
docker build -t "${IMAGE_NAME}" . >/dev/null

if ! kubectl cluster-info >/dev/null 2>&1; then
  echo "Docker Desktop Kubernetes is not reachable. Start the cluster, then rerun this script."
  exit 1
fi

echo "[3/8] Validating Kubernetes manifests"
for file in namespace resource-quota limit-range configmap secret persistent-volume persistent-volume-claim mysql-deployment deployment service ingress hpa network-policy; do
  kubectl apply --dry-run=client --validate=false -f "k8s/${file}.yaml" >/dev/null
done

echo "[4/8] Applying Kubernetes manifests"
for file in namespace resource-quota limit-range configmap secret persistent-volume persistent-volume-claim mysql-deployment deployment service ingress hpa network-policy; do
  kubectl apply -f "k8s/${file}.yaml" >/dev/null
done

echo "[5/8] Waiting for MySQL and application deployments"
kubectl rollout status deployment/day26-mysql -n "${NAMESPACE}" --timeout=240s >/dev/null
kubectl rollout status deployment/day26-platform -n "${NAMESPACE}" --timeout=240s >/dev/null

echo "[6/8] Verifying core Kubernetes resources"
kubectl get configmap day26-config -n "${NAMESPACE}" >/dev/null
kubectl get secret day26-secret -n "${NAMESPACE}" >/dev/null
kubectl get deployment day26-platform -n "${NAMESPACE}" >/dev/null
kubectl get service day26-platform-service -n "${NAMESPACE}" >/dev/null
kubectl get ingress day26-platform-ingress -n "${NAMESPACE}" >/dev/null

echo "[7/8] Port-forwarding service and executing API smoke tests"
kubectl port-forward -n "${NAMESPACE}" service/day26-platform-service 18091:8091 >"${PORT_FORWARD_LOG}" 2>&1 &
PORT_FORWARD_PID=$!
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/actuator/health" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

curl -sf "${BASE_URL}/actuator/health" | grep -q '"status":"UP"'
PLATFORM_RESPONSE="$(curl -sf "${BASE_URL}/api/platform/status")"
DEVICE_RESPONSE="$(curl -sf "${BASE_URL}/api/devices")"
TELEMETRY_RESPONSE="$(curl -sf "${BASE_URL}/api/telemetry")"
ALARM_RESPONSE="$(curl -sf "${BASE_URL}/api/alarms")"
DASHBOARD_RESPONSE="$(curl -sf "${BASE_URL}/api/dashboard")"
DEPLOYMENT_RESPONSE="$(curl -sf "${BASE_URL}/api/deployments")"
CLUSTER_RESPONSE="$(curl -sf "${BASE_URL}/api/cluster/health")"
NOTIFICATION_RESPONSE="$(curl -sf "${BASE_URL}/api/notifications")"

echo "[8/8] Validating markers"
grep -q "KUBERNETES_READY" <<< "${PLATFORM_RESPONSE}"
grep -q "POD_RUNNING" <<< "${DEVICE_RESPONSE}"
grep -q "CONFIGMAP_LOADED" <<< "${TELEMETRY_RESPONSE}"
grep -q "APPLICATION_HEALTHY" <<< "${ALARM_RESPONSE}"
grep -q "SERVICE_EXPOSED" <<< "${DASHBOARD_RESPONSE}"
grep -q "SECRET_LOADED" <<< "${NOTIFICATION_RESPONSE}"
grep -q "DEPLOYMENT_CREATED" <<< "${DEPLOYMENT_RESPONSE}"
grep -q "CLUSTER_READY" <<< "${CLUSTER_RESPONSE}"

echo "PASS"
