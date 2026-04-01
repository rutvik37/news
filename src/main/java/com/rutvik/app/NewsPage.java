package com.rutvik.app;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.concurrent.ThreadLocalRandom;

public class NewsPage extends BasePage {
    private static final String NEWS_LINK_SELECTOR = "main a[href*='/news/'], main h2 a, main h3 a";
    private static final int MAX_NEWS_CHOICES = 4;

    public NewsPage(Page page) {
        super(page);
    }

    public void clickRandomNewsTitle() {
        waitForPageReady();

        Locator newsLinks = page.locator(NEWS_LINK_SELECTOR);
        int newsCount = newsLinks.count();
        if (newsCount == 0) {
            throw new IllegalStateException("No news links were found in the main content area.");
        }

        int selectableNewsCount = Math.min(MAX_NEWS_CHOICES, newsCount);
        int randomIndex = ThreadLocalRandom.current().nextInt(selectableNewsCount);
        clickAndWait(newsLinks.nth(randomIndex));
    }
}
