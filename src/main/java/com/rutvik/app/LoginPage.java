package com.rutvik.app;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Handles login operations including city selection, mobile entry, OTP verification.
 */
public class LoginPage {
    private Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    /**
     * Reusable assertion helper - checks if a message is visible and reports detailed errors
     * @param messageToFind The exact message text to search for
     * @param fileName The Java file name for error reporting
     * @param methodName The method name where assertion is called
     * @param failureReason Description of what failed if message not found
     */
    private void assertMessageVisible(String messageToFind, String fileName, String methodName, String failureReason) {
        try {
            boolean isVisible = page.getByText(messageToFind).isVisible();
            if (!isVisible) {
                throw new AssertionError("Message not found on page!");
            }
            System.out.println("✓ ASSERTION PASSED: '" + messageToFind + "' message is displayed");
        } catch (Exception e) {
            System.err.println("\n════════════════════════════════════════════════════════════════");
            System.err.println("         ✗✗✗ ASSERTION FAILED ✗✗✗");
            System.err.println("════════════════════════════════════════════════════════════════");
            System.err.println("FILE: " + fileName);
            System.err.println("METHOD: " + methodName);
            System.err.println("");
            System.err.println("EXPECTED MESSAGE:");
            System.err.println("  → '" + messageToFind + "'");
            System.err.println("");
            System.err.println("ACTUAL MESSAGE ON PAGE:");
            System.err.println("  → NO MESSAGE FOUND / MESSAGE DOES NOT EXIST");
            System.err.println("");
            System.err.println("WHAT THIS MEANS:");
            System.err.println("  " + failureReason);
            System.err.println("");
            System.err.println("ERROR DETAILS: " + e.getMessage());
            System.err.println("════════════════════════════════════════════════════════════════\n");
        }
    }

    public void clickLoginButton() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("login Login")).click();
    }

    public void selectCity(String cityName) {
        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(cityName + " " + cityName)).check();
    }

    public void clickContinueButton() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
    }

    public void enterMobileNumber(String mobileNumber) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Mobile Number")).fill(mobileNumber);
    }

    public void enterOTP(String otp1, String otp2, String otp3, String otp4) {
        // Fast OTP entry without slowMo delays - type all digits at once
        page.getByRole(AriaRole.TEXTBOX).first().click();
        page.keyboard().type(otp1 + otp2 + otp3 + otp4);
    }

    public void clickVerifyAndContinue() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify & Continue")).click();
    }

    public void verifyOTPSentSuccessfully() {
        // Wait for up to 5 seconds for the OTP message to appear
        try {
            page.waitForTimeout(2000); // Wait 2 seconds for UI update (adjust as needed)
        } catch (Exception e) {
            System.err.println("Error during wait: " + e.getMessage());
        }
        // Debug print removed

        // Check for OTP success or rate-limit message
        boolean otpSent = false;
        boolean rateLimited = false;
        try {
            otpSent = page.getByText("OTP sent successfully").isVisible();
        } catch (Exception ignored) {}
        try {
            rateLimited = page.getByText("You’ve requested OTPs too frequently. For security reasons, please try again after a short wait.").isVisible();
        } catch (Exception ignored) {}

        if (otpSent) {
        } else if (rateLimited) {
            System.err.println("✗ OTP request was rate-limited. Please wait and try again later.");
        } else {
            assertMessageVisible("OTP sent successfully", "LoginPage.java", "verifyOTPSentSuccessfully()", "The OTP sending step may have failed.");
        }
    }

    public void completeLogin(String city, String mobileNumber, String otp1, String otp2, String otp3, String otp4) {
        clickLoginButton();
        selectCity(city);
        clickContinueButton();
        enterMobileNumber(mobileNumber);
        clickContinueButton();
        verifyOTPSentSuccessfully();
        enterOTP(otp1, otp2, otp3, otp4);
        clickVerifyAndContinue();
    }
}
