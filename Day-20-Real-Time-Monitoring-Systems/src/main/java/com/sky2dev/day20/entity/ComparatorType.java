package com.sky2dev.day20.entity;

public enum ComparatorType {
    GT,
    GTE,
    LT,
    LTE,
    EQ;

    public boolean evaluate(double metricValue, double threshold) {
        return switch (this) {
            case GT -> metricValue > threshold;
            case GTE -> metricValue >= threshold;
            case LT -> metricValue < threshold;
            case LTE -> metricValue <= threshold;
            case EQ -> Double.compare(metricValue, threshold) == 0;
        };
    }
}
