package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.nio.file.Paths;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    /**
     * Uploads a dummy profile image to test the change image functionality.
     */
    public void uploadDummyProfileImage() {
        // List of available images
        String[] images = {
            "src/resources/david-underland-GpPigjfrHuM-unsplash.jpg",
            "src/resources/pexels-atahandemir-14697083.jpg",
            "src/resources/pexels-jay-brand-1763356224-33151952.jpg"
        };
        // Pick a random image
        int idx = new Random().nextInt(images.length);
        page.setInputFiles(
            "input[type='file']",
            Paths.get(images[idx])
        );
    }

    public void editProfile() {
        clickEditProfile();

        // Random full name (min 3, max 7 chars per part, one space)
        String randomName = randomNamePart(3, 7) + " " + randomNamePart(3, 7);
        // Wait for the first text input to be visible, then fill
        page.locator("input[type='text']").first().waitFor();
        page.locator("input[type='text']").first().fill(randomName);


        // Random old DOB (not today or future, format DD/MM/YYYY)
        String dob = randomOldDOB_ddMMyyyy();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("DD/MM/YYYY")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("DD/MM/YYYY")).fill(dob);
        // Use user-provided sequence for reliable dropdown interaction
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("DD/MM/YYYY")).click();
        page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Edit Profile")).click();
        page.locator(".css-1xc3v61-indicatorContainer").click();

        uploadDummyProfileImage();
        clickSave();
        verifyProfileSavedSuccessfully();
    }

    // Helper to generate a random name part (optimized)
    private String randomNamePart(int minLen, int maxLen) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        int len = rand.nextInt(maxLen - minLen + 1) + minLen;
        char[] arr = new char[len];
        for (int i = 0; i < len; i++) {
            arr[i] = chars.charAt(rand.nextInt(chars.length()));
        }
        return new String(arr);
    }

    // Helper to generate a random old DOB in DD/MM/YYYY format (not today/future)
    private String randomOldDOB_ddMMyyyy() {
        Random rand = new Random();
        LocalDate today = LocalDate.now();
        LocalDate start = LocalDate.of(1950, 1, 1);
        long days = today.minusDays(1).toEpochDay() - start.toEpochDay();
        LocalDate dob = start.plusDays(rand.nextInt((int) days + 1));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dob.format(formatter);
    }

    // Helper to generate a random old DOB (yyyy-MM-dd, not today/future)
    private String randomOldDOB() {
        Random rand = new Random();
        LocalDate today = LocalDate.now();
        // Pick a date between 1950-01-01 and yesterday
        LocalDate start = LocalDate.of(1950, 1, 1);
        long days = today.minusDays(1).toEpochDay() - start.toEpochDay();
        LocalDate dob = start.plusDays(rand.nextInt((int) days + 1));
        return dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
