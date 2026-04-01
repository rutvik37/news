package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles logout operations.
 */
public class LogoutPage extends BasePage {
    public LogoutPage(Page page) {
        super(page);
    }

    public void clickLogout() {
        byRole(AriaRole.BUTTON, "Logout").click();
    }

    public void clickConfirmLogout() {
        clickAndWait(byRole(AriaRole.BUTTON, "Yes, logout!"));
    }

    public void logout() {
        openMenu();
        clickLogout();
        clickConfirmLogout();
    }
}
