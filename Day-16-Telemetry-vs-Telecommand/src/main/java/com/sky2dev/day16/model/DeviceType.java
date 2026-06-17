package com.sky2dev.day16.model;

public enum DeviceType {
    ROUTER_01("Router-01", "Telecom", true, true, false, true),
    SWITCH_01("Switch-01", "Telecom", true, false, false, false),
    HUB_01("Hub-01", "Industrial", true, false, false, false),
    MODEM_01("Modem-01", "SatCom", true, true, true, true),
    BUC_01("BUC-01", "SatCom", false, true, true, true),
    LNB_01("LNB-01", "SatCom", false, true, false, true),
    PLC_01("PLC-01", "Industrial", true, false, false, false),
    SENSOR_01("Sensor-01", "Industrial", false, false, false, false);

    private final String displayName;
    private final String category;
    private final boolean supportsInterfaceControl;
    private final boolean supportsCarrierControl;
    private final boolean supportsModemReset;
    private final boolean supportsFrequencyControl;

    DeviceType(String displayName, String category, boolean supportsInterfaceControl, boolean supportsCarrierControl, boolean supportsModemReset, boolean supportsFrequencyControl) {
        this.displayName = displayName;
        this.category = category;
        this.supportsInterfaceControl = supportsInterfaceControl;
        this.supportsCarrierControl = supportsCarrierControl;
        this.supportsModemReset = supportsModemReset;
        this.supportsFrequencyControl = supportsFrequencyControl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCategory() {
        return category;
    }

    public boolean supportsInterfaceControl() {
        return supportsInterfaceControl;
    }

    public boolean supportsCarrierControl() {
        return supportsCarrierControl;
    }

    public boolean supportsModemReset() {
        return supportsModemReset;
    }

    public boolean supportsFrequencyControl() {
        return supportsFrequencyControl;
    }
}

