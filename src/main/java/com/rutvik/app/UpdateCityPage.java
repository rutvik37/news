package com.rutvik.app;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles city update operations.
 */
public class UpdateCityPage extends BasePage {
    public UpdateCityPage(Page page) {
        super(page);
    }

    public void clickUpdateCity() {
        clickAndWait(byRole(AriaRole.LINK, "Update City Update City"));
    }

    public void selectCity(String cityName) {
        page.locator("label").filter(new Locator.FilterOptions().setHasText(cityName)).click();
    }

    public void clickContinue() {
        byRole(AriaRole.BUTTON, "Continue").click();
    }

    public void verifyCityUpdatedSuccessfully() {
        waitForMessage(
            "City preference updated successfully",
            "The city update operation may have failed."
        );
    }

    public void updateCity(String cityName) {
        openMenu();
        clickUpdateCity();
        selectCity(cityName);
        clickContinue();
        verifyCityUpdatedSuccessfully();
    }
}
