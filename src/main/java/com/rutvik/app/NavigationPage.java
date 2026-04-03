package com.rutvik.app;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles navigation operations like clicking Home, Video Gallery, Photo Gallery, etc.
 */
public class NavigationPage extends BasePage {
    private static final String[] ZODIAC_SIGNS = {
        "Taurus",
        "Gemini",
        "Aries",
        "Leo",
        "Libra",
        "Virgo",
        "Sagittarius",
        "Capricorn",
        "Aquarius",
        "Cancer",
        "Scorpio",
        "Pisces"
    };

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

    public void viewCityNews() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("City")).click();
        page.waitForTimeout(1200);
        page.getByRole(AriaRole.BANNER)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Ahmedabad"))
            .click();
        page.waitForTimeout(1200);
        page.getByRole(AriaRole.BANNER)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Amreli"))
            .click();
        page.waitForTimeout(1200);
        page.getByRole(AriaRole.BANNER)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Arvali"))
            .click();
        page.waitForTimeout(1200);

        page.getByRole(AriaRole.BANNER)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Astrology"))
            .click();
        page.waitForTimeout(1200);
        String randomSign = ZODIAC_SIGNS[ThreadLocalRandom.current().nextInt(ZODIAC_SIGNS.length)];
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("image " + randomSign)).click();
        System.out.println("Astrology sign selected: " + randomSign);
        page.waitForTimeout(1200);
        page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Annual Horoscope")).click();
        page.waitForTimeout(1200);
    }

    public void clickOpenMenu() {
        openMenu();
    }

    public void navigateToUrl(String url) {
        navigateTo(url);
    }
}
