package com.rutvik.app;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles notification operations.
 */
public class NotificationsPage extends BasePage {
    public NotificationsPage(Page page) {
        super(page);
    }

    public void clickOpenNotifications() {
        byRole(AriaRole.BUTTON, "Open notifications").click();
    }

    public void clickReadMore() {
        Locator banner = page.getByRole(AriaRole.BANNER);
        clickAndWait(
            banner.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Read More..."))
        );
    }

    public void viewNotifications() {
        clickOpenNotifications();
        clickReadMore();
    }
}
