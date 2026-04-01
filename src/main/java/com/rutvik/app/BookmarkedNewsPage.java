package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles bookmarked news operations.
 */
public class BookmarkedNewsPage extends BasePage {
    public BookmarkedNewsPage(Page page) {
        super(page);
    }

    public void clickBookmarkedNews() {
        clickAndWait(byRole(AriaRole.LINK, "Bookmark icon Bookmarked News"));
    }

    public void viewBookmarkedNews() {
        openMenu();
        clickBookmarkedNews();
    }
}
