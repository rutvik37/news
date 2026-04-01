package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles navigation operations like clicking Home, Video Gallery, Photo Gallery, etc.
 */
public class NavigationPage extends BasePage {
    public NavigationPage(Page page) {
        super(page);
    }

    public void clickHome() {
        clickAndWait(byRole(AriaRole.LINK, "Home", true));
    }

    public void clickVideoGallery() {
        clickAndWait(byRole(AriaRole.LINK, "Video Gallery"));
    }

    public void clickPhotoGallery() {
        clickAndWait(byRole(AriaRole.LINK, "Photo Gallery"));
    }

    public void clickOpenMenu() {
        openMenu();
    }

    public void navigateToUrl(String url) {
        navigateTo(url);
    }
}
