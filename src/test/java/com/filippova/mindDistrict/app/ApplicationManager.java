package com.filippova.mindDistrict.app;

import com.filippova.mindDistrict.app.helpers.CatalogueHelper;
import com.filippova.mindDistrict.app.helpers.LoginHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;

import static org.openqa.selenium.remote.Browser.CHROME;
import static org.openqa.selenium.remote.Browser.FIREFOX;

@Slf4j
@RequiredArgsConstructor
@Getter
public class ApplicationManager {

    private final static String HOMEPAGE_URL = "https://codingtask.minddistrict.dev";

    private final Browser browser;

    private WebDriver wd;
    private LoginHelper loginHelper;
    private CatalogueHelper catalogueHelper;

    public void init() {
        String webdriverLocation = getProperties().getProperty(browser.browserName());

        if (CHROME.equals(browser)) {
            System.setProperty("webdriver.chrome.driver", webdriverLocation);
            wd = initWebDriver(ChromeDriver.class);
        } else if (FIREFOX.equals(browser)) {
            System.setProperty("webdriver.gecko.driver", webdriverLocation);
            wd = initWebDriver(FirefoxDriver.class);
        }

        wd.manage().window().maximize();
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        loginHelper = new LoginHelper(wd);
        catalogueHelper = new CatalogueHelper(wd);
    }

    public void stop() {
        wd.quit();
    }

    public void navigateToHomePage() {
        if (wd != null) {
            wd.navigate().to(HOMEPAGE_URL);
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("webdriver.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Can't load webdriver properties", e);
        }
        return properties;
    }

    private <T extends WebDriver> WebDriver initWebDriver(Class<T> typeOfWebDriver) {
        try {
            return new EventFiringDecorator<T>(new LoggingWebDriverEventListener())
                    .decorate(typeOfWebDriver.getDeclaredConstructor().newInstance());
        } catch (Exception ex) {
            log.error("Error while initializing WebDriver", ex);
            throw new RuntimeException(ex);
        }

    }

    @Slf4j
    public static class LoggingWebDriverEventListener implements WebDriverListener {

        @Override
        public void beforeFindElement(WebDriver driver, By locator) {
            log.info("Start search by " + locator);
        }

        @Override
        public void afterFindElement(WebDriver driver, By locator, WebElement result) {
            log.info("Element found by " + locator);
        }

        @Override
        public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
            log.error("Error while finding element: " + e.getLocalizedMessage(), e);
        }
    }
}
