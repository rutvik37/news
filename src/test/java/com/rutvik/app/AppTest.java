package com.rutvik.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

public class AppTest {
    @After
    public void clearProperties() {
        System.clearProperty("playwright.headless");
        System.clearProperty("playwright.slowMoMs");
        System.clearProperty("playwright.pauseMs");
    }

    @Test
    public void usesDefaultExecutionSettingsWhenNoOverridesExist() {
        ExecutionSettings settings = ExecutionSettings.fromSystemProperties();

        assertFalse(settings.isHeadless());
        assertEquals(ExecutionSettings.DEFAULT_SLOW_MO_MS, settings.getSlowMoMs());
        assertEquals(ExecutionSettings.DEFAULT_PAUSE_MS, settings.getPauseAfterFlowMs());
    }

    @Test
    public void appliesExecutionSettingOverridesFromSystemProperties() {
        System.setProperty("playwright.headless", "true");
        System.setProperty("playwright.slowMoMs", "75");
        System.setProperty("playwright.pauseMs", "500");

        ExecutionSettings settings = ExecutionSettings.fromSystemProperties();

        assertTrue(settings.isHeadless());
        assertEquals(75, settings.getSlowMoMs());
        assertEquals(500, settings.getPauseAfterFlowMs());
    }

    @Test
    public void fallsBackToDefaultsForInvalidNumericOverrides() {
        System.setProperty("playwright.slowMoMs", "-10");
        System.setProperty("playwright.pauseMs", "invalid");

        ExecutionSettings settings = ExecutionSettings.fromSystemProperties();

        assertEquals(ExecutionSettings.DEFAULT_SLOW_MO_MS, settings.getSlowMoMs());
        assertEquals(ExecutionSettings.DEFAULT_PAUSE_MS, settings.getPauseAfterFlowMs());
    }
}
