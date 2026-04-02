package com.rutvik.app;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class Main {
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
        navigationPage.navigateToUrl(ExecutionSettings.BASE_URL);
        navigationPage.clickHome();
        navigationPage.clickVideoGallery();
        navigationPage.clickPhotoGallery();

        int step = 1;
        searchPage.clickOneRandomCity();
        System.out.println((step++) + ". SearchPage DONE");
        newsPage.clickRandomNewsTitle();
        System.out.println((step++) + ". NewsPage DONE");
        navigationPage.clickHome();
        navigationPage.clickOpenMenu();
        String otp = ExecutionSettings.DEFAULT_OTP;
        loginPage.completeLogin(
            ExecutionSettings.DEFAULT_LOGIN_CITY,
            ExecutionSettings.DEFAULT_MOBILE_NUMBER,
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
        historyNewsPage.navigateToHistory();
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
}
