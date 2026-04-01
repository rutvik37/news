package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles profile-related operations like editing profile and saving changes.
 */
public class ProfilePage extends BasePage {
    public ProfilePage(Page page) {
        super(page);
    }

    public void clickEditProfile() {
        clickAndWait(byRole(AriaRole.LINK, "Profile icon Edit Profile"));
    }

    public void clickSave() {
        byRole(AriaRole.BUTTON, "Save").click();
    }

    public void verifyProfileSavedSuccessfully() {
        waitForMessage(
            "Personal details added successfully",
            "The profile save operation may have failed."
        );
    }

    public void editProfile() {
        clickEditProfile();
        clickSave();
        verifyProfileSavedSuccessfully();
    }
}
