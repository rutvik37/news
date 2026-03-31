package com.rutvik.app;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class App 
{
    public static void main( String[] args ) throws InterruptedException 
    {
        
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1500));

            
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.gujaratshortnews.com/");
            Thread.sleep(2000);

    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Video Gallery")).click();
    
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Photo Gallery")).click();
    
    page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("search-input")).click();
    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open menu")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("login Login")).click();
    
    page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Ahmedabad Ahmedabad")).check();
    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
    
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Mobile Number")).fill("8200124611");
    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
    
    page.getByRole(AriaRole.TEXTBOX).first().click();
    page.getByRole(AriaRole.TEXTBOX).first().fill("1");
    page.getByRole(AriaRole.TEXTBOX).nth(1).fill("2");
    page.getByRole(AriaRole.TEXTBOX).nth(2).fill("3");
    page.getByRole(AriaRole.TEXTBOX).nth(3).fill("4");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify & Continue")).click();
    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open menu")).click();
    
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Profile icon Edit Profile")).click();
    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open menu")).click();

    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bookmark icon Bookmarked News")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open menu")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("History news icon History News")).click();
    page.navigate("https://www.gujaratshortnews.com/newshistory");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open menu")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Update City Update City")).click();
    page.locator("label").filter(new Locator.FilterOptions().setHasText("Anad")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open notifications")).click();
    
    page.getByRole(AriaRole.BANNER).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Read More...")).click();
    
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home").setExact(true)).click();
    
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open menu")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logout")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes, logout!")).click();

            
            Thread.sleep(2000);
            browser.close();
    }
}
}

