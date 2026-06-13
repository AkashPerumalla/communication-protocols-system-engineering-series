public final class PollingStatistics {
    private int requestsSent;
    private int responsesReceived;
    private long bytesTransferred;
    private long executionTimeNanos;

    public void recordRequest() {
        requestsSent++;
    }

    public void recordResponse(SnmpResponse response) {
        responsesReceived++;
        bytesTransferred += estimateBytes(response);
        executionTimeNanos += Math.max(0L, response.elapsedNanos());
    }

    public void recordResponses(Iterable<SnmpResponse> responses) {
        for (SnmpResponse response : responses) {
            recordResponse(response);
        }
    }

    public int requestsSent() {
        return requestsSent;
    }

    public int responsesReceived() {
        return responsesReceived;
    }

    public long bytesTransferred() {
        return bytesTransferred;
    }

    public long executionTimeNanos() {
        return executionTimeNanos;
    }

    public long executionTimeMillis() {
        return Math.max(0L, executionTimeNanos / 1_000_000L);
    }

    public double efficiencyRatio() {
        if (requestsSent == 0) {
            return 0.0d;
        }
        return (double) responsesReceived / (double) requestsSent;
    }

    public String summaryLine() {
        return "Requests Sent: " + requestsSent
                + ", Responses Received: " + responsesReceived
                + ", Bytes Transferred: " + bytesTransferred
                + ", Execution Time: " + executionTimeMillis() + " ms"
                + ", Efficiency Ratio: " + String.format("%.2f", efficiencyRatio());
    }

    private long estimateBytes(SnmpResponse response) {
        return 32L
                + response.requestOid().length()
                + response.resolvedOid().length()
                + response.objectName().length()
                + response.dataType().length()
                + response.value().length()
                + response.status().length()
                + response.message().length();
    }
}
