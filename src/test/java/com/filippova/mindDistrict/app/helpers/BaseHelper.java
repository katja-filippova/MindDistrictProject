package com.filippova.mindDistrict.app.helpers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public abstract class BaseHelper {

    private final WebDriver wd;
    static final int DEFAULT_SLEEP_IN_MILLIS = 1000;

    void fillInInputData(By locator, String inputInfo) {
        if (inputInfo != null) {
            findElementByLocatorAndClick(locator);
            wd.findElement(locator).clear();
            wd.findElement(locator).sendKeys(inputInfo);
        }
    }

    By getElementLocatorByLabel(String checkboxLabel) {
        return By.xpath("//*[ contains (text(), \"" + checkboxLabel + "\" ) ]");
    }

    void findElementByLocatorAndClick(By locator) {
        wd.findElement(locator).click();
    }

    public boolean isElementPresentByLocator(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException | NoSuchElementException ignored) {
            return false;
        }
        return true;
    }

    public boolean isCookieWithNamePresent(String cookieName) {
        Optional<Cookie> cookie = Optional.ofNullable(wd.manage().getCookieNamed(cookieName));
        return cookie.isPresent();
    }

    public void scrollElementIntoView(By elementLocator) throws InterruptedException {
        scrollElementIntoViewWithCorrection(elementLocator, null);
    }

    void scrollToElementAndClick(By locator) throws InterruptedException {
        scrollElementIntoView(locator);
        findElementByLocatorAndClick(locator);
    }

    void scrollElementIntoViewWithCorrection(By elementLocator, Integer verticalCorrectionInPixels) throws InterruptedException {
        defaultSleep();
        WebElement element = wd.findElement(elementLocator);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) wd;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        Optional.ofNullable(verticalCorrectionInPixels).ifPresent(value -> jsExecutor.executeScript("window.scrollBy(0," + value +")", ""));
        defaultSleep();
    }

    void scrollToElementWithCorrectionAndClick(By locator, Integer verticalCorrectionInPixels) throws InterruptedException {
        scrollElementIntoViewWithCorrection(locator, verticalCorrectionInPixels);
        findElementByLocatorAndClick(locator);
    }

    void scrollToElementWithCorrectionAndFillInRandomData(By locator, Integer verticalCorrectionInPixels) throws InterruptedException {
        scrollElementIntoViewWithCorrection(locator, verticalCorrectionInPixels);
        fillInInputData(locator, getRandomString());
    }

    public String takeScreenshot() throws IOException {
        File tmp = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
        File screenshot = new File("screenshot" + System.currentTimeMillis() + ".png");
        Files.copy(tmp.toPath(), screenshot.toPath());
        return screenshot.getAbsolutePath();
    }

    String getRandomString() {
        final int minStringLength = 10;
        final int maxStringLength = 50;
        int randomStringLength = ThreadLocalRandom.current().nextInt(minStringLength, maxStringLength + 1);
        return RandomStringUtils.random(randomStringLength, true, false);
    }

    public void defaultSleep() throws InterruptedException {
        Thread.sleep(DEFAULT_SLEEP_IN_MILLIS);
    }
}
