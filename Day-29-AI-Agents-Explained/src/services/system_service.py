import shutil

import psutil

from src.config.settings import AppSettings


class SystemService:
    def __init__(self, settings: AppSettings) -> None:
        self._settings = settings

    def system_info(self) -> dict[str, float]:
        if self._settings.deterministic_mode:
            return {
                "cpu_percent": 21.0,
                "memory_total_mb": 8192.0,
                "memory_used_mb": 4096.0,
                "disk_total_gb": 256.0,
                "disk_used_gb": 96.0,
            }

        vm = psutil.virtual_memory()
        disk = shutil.disk_usage("/")
        return {
            "cpu_percent": round(psutil.cpu_percent(interval=0.0), 2),
            "memory_total_mb": round(vm.total / (1024 * 1024), 2),
            "memory_used_mb": round(vm.used / (1024 * 1024), 2),
            "disk_total_gb": round(disk.total / (1024 * 1024 * 1024), 2),
            "disk_used_gb": round(disk.used / (1024 * 1024 * 1024), 2),
        }
