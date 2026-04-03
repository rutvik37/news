package com.rutvik.app;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.Arrays;
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
    private static final String[] TODAY_ASTRO_SIGNS = {
        "Aries",
        "Taurus",
        "Gemini",
        "Cancer",
        "Leo",
        "Virgo",
        "Libra",
        "Scorpio",
        "Sagittarius",
        "Capricorn",
        "Aquarius",
        "Pisces"
    };
    /** Today Astro dropdown options after Aries (index 0); Aries is default and not re-selected. */
    private static final String[] TODAY_ASTRO_NON_ARIES =
        Arrays.copyOfRange(TODAY_ASTRO_SIGNS, 1, TODAY_ASTRO_SIGNS.length);

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

        scrollSlowlyToTodayAstro();
        page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Today Astro")).click();
        page.waitForTimeout(1200);
        // Aries is already open by default — do not select it again; pick 2 random other signs only.
        String selectedSign = "Aries";
        String[] twoMoreSigns = pickTwoRandomDistinctNonAries();
        System.out.println(
            "Today Astro: keeping default Aries; opening 2 random signs -> "
                + twoMoreSigns[0]
                + ", "
                + twoMoreSigns[1]
        );
        for (String sign : twoMoreSigns) {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(selectedSign)).click();
            page.waitForTimeout(800);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(sign)).click();
            System.out.println("Today Astro sign selected: " + sign);
            page.waitForTimeout(1200);
            selectedSign = sign;
        }
    }

    private static String[] pickTwoRandomDistinctNonAries() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int firstIndex = random.nextInt(TODAY_ASTRO_NON_ARIES.length);
        int secondIndex;
        do {
            secondIndex = random.nextInt(TODAY_ASTRO_NON_ARIES.length);
        } while (secondIndex == firstIndex);
        return new String[] {
            TODAY_ASTRO_NON_ARIES[firstIndex],
            TODAY_ASTRO_NON_ARIES[secondIndex]
        };
    }

    private void scrollSlowlyToTodayAstro() {
        Locator todayAstroHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Today Astro"));
        for (int step = 0; step < 48 && !todayAstroHeading.isVisible(); step++) {
            page.mouse().wheel(0, 320);
            page.waitForTimeout(130);
        }
        todayAstroHeading.scrollIntoViewIfNeeded();
        page.waitForTimeout(400);
    }

    public void clickOpenMenu() {
        openMenu();
    }

    public void navigateToUrl(String url) {
        navigateTo(url);
    }
}
