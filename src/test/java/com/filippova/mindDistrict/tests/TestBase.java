package com.filippova.mindDistrict.tests;

import com.filippova.mindDistrict.app.ApplicationManager;
import com.filippova.mindDistrict.app.helpers.CatalogueHelper;
import com.filippova.mindDistrict.app.helpers.LoginHelper;
import com.filippova.mindDistrict.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.Browser;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

@Slf4j
public abstract class TestBase {

    private static final Browser BROWSER = Browser.CHROME; // = Browser.FIREFOX;

    private final ApplicationManager app = new ApplicationManager(BROWSER);

    protected LoginHelper loginHelper;
    protected CatalogueHelper catalogueHelper;

    @BeforeMethod
    public void beforeMethod(Method m, Object[] objects) {
        log.info("Start test " + m.getName());

        app.init();
        app.navigateToHomePage();

        loginHelper = app.getLoginHelper();
        catalogueHelper = app.getCatalogueHelper();
    }


    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws IOException {
        if (result.isSuccess()) {
            log.info("PASSED: test method " + result.getMethod().getMethodName());
        } else {
            log.error("FAILED: test method " + result.getMethod().getMethodName() + "\n" + "Screenshot: " + loginHelper.takeScreenshot());
        }
        app.stop();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    void loginUserWithValidCredentials() throws InterruptedException {
        final String userEmail = app.getAppProperties().getProperty("userEmail");
        final String userPassword = app.getAppProperties().getProperty("userPassword");

        final User user = User.builder().email(userEmail).password(userPassword).build();

        // logging in with valid credentials
        loginHelper.fillInLoginFormAndClickSubmitButton(user);
    }
}
