package com.rutvik.app;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class Main {
    static final String BASE_URL = "https://www.gujaratshortnews.com/";
    static final String HISTORY_URL = BASE_URL + "newshistory";
    static final String DEFAULT_LOGIN_CITY = "Ahmedabad";
    static final String DEFAULT_MOBILE_NUMBER = "8200124611";
    static final String DEFAULT_OTP = "1234";
    static final boolean DEFAULT_HEADLESS = false;
    static final int DEFAULT_SLOW_MO_MS = 1000;
    static final int DEFAULT_PAUSE_MS = 0;

    private Main() {
    }

    public static void main(String[] args) {
        ExecutionSettings settings = ExecutionSettings.fromSystemProperties();

        try (
            Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(buildLaunchOptions(settings));
            BrowserContext context = browser.newContext()
        ) {
            Page page = context.newPage();
            runSmokeFlow(page);

            if (settings.getPauseAfterFlowMs() > 0) {
                page.waitForTimeout(settings.getPauseAfterFlowMs());
            }
        }
    }

    private static BrowserType.LaunchOptions buildLaunchOptions(ExecutionSettings settings) {
        return new BrowserType.LaunchOptions()
            .setHeadless(settings.isHeadless())
            .setSlowMo((double) settings.getSlowMoMs());
    }

    private static void runSmokeFlow(Page page) {
        NavigationPage navigationPage = new NavigationPage(page);
        SearchPage searchPage = new SearchPage(page);
        NewsPage newsPage = new NewsPage(page);
        LoginPage loginPage = new LoginPage(page);
        ProfilePage profilePage = new ProfilePage(page);
        BookmarkedNewsPage bookmarkedNewsPage = new BookmarkedNewsPage(page);
        HistoryNewsPage historyNewsPage = new HistoryNewsPage(page);
        UpdateCityPage updateCityPage = new UpdateCityPage(page);
        NotificationsPage notificationsPage = new NotificationsPage(page);
        LogoutPage logoutPage = new LogoutPage(page);
        navigationPage.navigateToUrl(BASE_URL);
        navigationPage.clickHome();
        navigationPage.clickVideoGallery();
        navigationPage.clickPhotoGallery();

        int step = 1;
        navigationPage.clickHome();
        navigationPage.viewCityNews();
        System.out.println((step++) + ". CityNews DONE");
        navigationPage.clickHome();
        searchPage.clickOneRandomCity();
        System.out.println((step++) + ". SearchPage DONE");
        newsPage.clickRandomNewsTitle();
        System.out.println((step++) + ". NewsPage DONE");
        navigationPage.clickHome();
        navigationPage.clickOpenMenu();
        String otp = DEFAULT_OTP;
        loginPage.completeLogin(
            DEFAULT_LOGIN_CITY,
            DEFAULT_MOBILE_NUMBER,
            String.valueOf(otp.charAt(0)),
            String.valueOf(otp.charAt(1)),
            String.valueOf(otp.charAt(2)),
            String.valueOf(otp.charAt(3))
        );
        System.out.println((step++) + ". LoginPage DONE");

        navigationPage.clickOpenMenu();
        profilePage.editProfile();
        System.out.println((step++) + ". ProfilePage DONE");
        bookmarkedNewsPage.viewBookmarkedNews();
        System.out.println((step++) + ". BookmarkedNewsPage DONE");
        historyNewsPage.navigateToHistory(HISTORY_URL);
        System.out.println((step++) + ". HistoryNewsPage DONE");
        updateCityPage.updateCity("Anad");
        System.out.println((step++) + ". UpdateCityPage DONE");
        notificationsPage.viewNotifications();
        System.out.println((step++) + ". NotificationsPage DONE");
        navigationPage.clickHome();
        logoutPage.logout();
        System.out.println((step++) + ". LogoutPage DONE");
        System.out.println((step++) + ". NavigationPage DONE");
        System.out.println((step) + ". Main DONE");
    }

    static final class ExecutionSettings {
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

        private static boolean readBooleanProperty(String propertyName, boolean defaultValue) {
            String value = System.getProperty(propertyName);
            if (value == null || value.isBlank()) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value.trim());
        }

        private static int readIntProperty(String propertyName, int defaultValue) {
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
}
