package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles history news operations.
 */
public class HistoryNewsPage extends BasePage {
    public HistoryNewsPage(Page page) {
        super(page);
    }

    public void clickHistoryNews() {
        byRole(AriaRole.LINK, "History news icon History News").click();
    }

    public void navigateToHistory(String historyUrl) {
        openMenu();
        try {
            clickHistoryNews();
            page.waitForURL("**/newshistory");
            waitForPageReady();
        } catch (PlaywrightException exception) {
            navigateTo(historyUrl);
        }
    }
}
