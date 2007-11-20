package com.gtrobot.command;

public enum ErrorType {
    wrongParameter(false), reEnterInteractive(true), reEnterSameInteractive(
            true), accountLocked(true);

    private boolean abnormal;

    ErrorType(final boolean abnormal) {
        this.abnormal = abnormal;
    }

    public boolean isAbnormal() {
        return this.abnormal;
    }

}