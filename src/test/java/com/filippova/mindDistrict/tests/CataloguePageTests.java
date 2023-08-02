package com.filippova.mindDistrict.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CataloguePageTests extends TestBase {

    @Test
    public void testThatUserIsAbleToSelectAndCompleteTrainingFromCatalogue() throws Exception {
        // logging in user and navigate to catalogue page
        loginUserAndNavigateToCataloguePage();

        // selecting and completing training
        catalogueHelper.selectAndCompleteTrainingFromCatalogue();

        // navigating back to home page and verifying training completion
        catalogueHelper.navigateToHomePage();
        By finishedStatusTextLocator = By.xpath("//div[@id='primary-tools']/div[1]/a/div[1]/span/span[text()='Finished']");
        assertTrue(loginHelper.isElementPresentByLocator(finishedStatusTextLocator));
    }

    @Test
    public void testThatUserIsAbleToAddAndFinishDiaryFromCatalogue() throws Exception {
        // logging in user and navigate to catalogue page
        loginUserAndNavigateToCataloguePage();

        // selecting and completing diary
        catalogueHelper.selectAndCompleteDiaryFromCatalogue();

        // navigating back to home page and verifying diary completion
        catalogueHelper.navigateToHomePage();
        By savedDiaryLocator = By.xpath("//div[@id='secondary-tools']/div[1]/a");
        catalogueHelper.scrollElementIntoView(savedDiaryLocator);
        assertTrue(loginHelper.isElementPresentByLocator(savedDiaryLocator));
    }

    private void loginUserAndNavigateToCataloguePage() throws InterruptedException {
        // logging in user
        loginUserWithValidCredentials();

        // navigating to catalogue page
        catalogueHelper.defaultSleep();
        catalogueHelper.navigateToCataloguePage();
    }
}
