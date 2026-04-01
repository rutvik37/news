package com.rutvik.app;

final class ExecutionSettings {
    static final String BASE_URL = "https://www.gujaratshortnews.com/";
    static final String HISTORY_URL = BASE_URL + "newshistory";
    static final String DEFAULT_LOGIN_CITY = "Ahmedabad";
    static final String DEFAULT_MOBILE_NUMBER = "8200124611";
    static final String DEFAULT_OTP = "1234";
    static final boolean DEFAULT_HEADLESS = false;
    static final int DEFAULT_SLOW_MO_MS = 1000;
    static final int DEFAULT_PAUSE_MS = 0;

    private final boolean headless;
    private final int slowMoMs;
    private final int pauseAfterFlowMs;

    private ExecutionSettings(boolean headless, int slowMoMs, int pauseAfterFlowMs) {
        this.headless = headless;
        this.slowMoMs = slowMoMs;
        this.pauseAfterFlowMs = pauseAfterFlowMs;
    }

    static ExecutionSettings fromSystemProperties() {
        return new ExecutionSettings(
            readBooleanProperty("playwright.headless", DEFAULT_HEADLESS),
            readIntProperty("playwright.slowMoMs", DEFAULT_SLOW_MO_MS),
            readIntProperty("playwright.pauseMs", DEFAULT_PAUSE_MS)
        );
    }

    boolean isHeadless() {
        return headless;
    }

    int getSlowMoMs() {
        return slowMoMs;
    }

    int getPauseAfterFlowMs() {
        return pauseAfterFlowMs;
    }

    static boolean readBooleanProperty(String propertyName, boolean defaultValue) {
        String value = System.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    static int readIntProperty(String propertyName, int defaultValue) {
        String value = System.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            int parsedValue = Integer.parseInt(value.trim());
            return parsedValue >= 0 ? parsedValue : defaultValue;
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}
