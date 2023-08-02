package com.filippova.mindDistrict.tests;

import com.filippova.mindDistrict.entity.User;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LoginPageTests extends TestBase {

    private static final String INVALID_TEST_EMAIL = "invalid@minddistrict.ru";
    private static final String INVALID_PASSWORD = "invalid_password";

    private static final String EXPECTED_AUTH_COOKIE_NAME = "__Host-minddistrict.id";

    @Test
    public void testUserLoginWithValidCredentials() throws InterruptedException {
        // logging in with valid credentials
        loginUserWithValidCredentials();

        // checking in user avatar and auth cookie
        assertTrue(loginHelper.isElementPresentByLocator(By.className("avatar__user")));
        assertTrue(loginHelper.isCookieWithNamePresent(EXPECTED_AUTH_COOKIE_NAME));
    }

    @Test
    public void testUserLoginWithInvalidCredentials() throws InterruptedException {
        // logging in with invalid credentials
        final User user = User.builder().email(INVALID_TEST_EMAIL).password(INVALID_PASSWORD).build();
        loginHelper.fillInLoginFormAndClickSubmitButton(user);

        // checking in error notification message and auth cookie absence
        assertTrue(loginHelper.isElementPresentByLocator(By.className("notice__message")));
        assertFalse(loginHelper.isCookieWithNamePresent(EXPECTED_AUTH_COOKIE_NAME));
    }
}
