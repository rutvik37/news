package com.rutvik.app;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

abstract class BasePage {
    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected Locator byRole(AriaRole role, String accessibleName) {
        return page.getByRole(role, new Page.GetByRoleOptions().setName(accessibleName));
    }

    protected Locator byRole(AriaRole role, String accessibleName, boolean exact) {
        return page.getByRole(
            role,
            new Page.GetByRoleOptions().setName(accessibleName).setExact(exact)
        );
    }

    protected void waitForPageReady() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    protected void navigateTo(String url) {
        page.navigate(url);
        waitForPageReady();
    }

    protected void clickAndWait(Locator locator) {
        locator.click();
        waitForPageReady();
    }

    protected void openMenu() {
        byRole(AriaRole.BUTTON, "Open menu").click();
    }

    protected void waitForMessage(String message, String failureReason) {
        Locator messageLocator = page.getByText(message).first();
        try {
            messageLocator.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
            );
        } catch (PlaywrightException exception) {
            throw new AssertionError(
                failureReason + " Expected visible text: '" + message + "'.",
                exception
            );
        }
    }
}
