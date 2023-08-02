package com.filippova.mindDistrict.app.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CatalogueHelper extends BaseHelper {

    private final static int HEADER_HEIGHT_CORRECTION_IN_PIXELS = -64;

    public CatalogueHelper(WebDriver wd) {
        super(wd);
    }

    public void navigateToCataloguePage() {
        final By catalogueNavigationButtonLocator = By.xpath("//nav[@class='header__navigation']/ul/li[2]");
        findElementByLocatorAndClick(catalogueNavigationButtonLocator);
    }

    public void navigateToHomePage() {
        final By homePageNavigationButtonLocator = By.xpath("//nav[@class='header__navigation']/ul/li[1]");
        findElementByLocatorAndClick(homePageNavigationButtonLocator);
    }

    public void selectAndCompleteTrainingFromCatalogue() throws InterruptedException {
        // selecting module
        final By moduleForTestAutomationLinkLocator = By.xpath("//ul[@class='catalogue-cards']/li[3]");
        scrollToElementAndClick(moduleForTestAutomationLinkLocator);

        // starting module
        final By startModuleButtonLocator = By.id("form_actions_start");
        findElementByLocatorAndClick(startModuleButtonLocator);

        // filling in input form
        final By insertFieldLocator = By.id("2d955666834b4becad80d67e67388ca4");
        fillInInputData(insertFieldLocator, getRandomString());

        // selecting checkbox
        final By numberOfRatsCheckboxOptionLocator = getElementLocatorByLabel("5-7");
        scrollToElementAndClick(numberOfRatsCheckboxOptionLocator);

        // selecting radiobutton
        final By kindOfRatRadiobuttonOptionLocator = getElementLocatorByLabel("A skinny rat.");
        scrollToElementAndClick(kindOfRatRadiobuttonOptionLocator);

        // navigate to page 2
        scrollToContinueButtonAndClick();
        // navigate to page 3
        scrollToContinueButtonAndClick();
        // navigate to page 4
        final By tryMiceOptionButtonLocator = By.xpath("//element-section-options/div/div/button[2]");
        scrollToElementAndClick(tryMiceOptionButtonLocator);
        // navigate to page 5
        scrollToContinueButtonAndClick();
        scrollToContinueButtonAndClick();
        // navigate to page 6
        scrollToContinueButtonAndClick();

        // complete survey
        scrollToContinueButtonAndClick();
        defaultSleep();
    }

    public void selectAndCompleteDiaryFromCatalogue() throws InterruptedException {
        // selecting diary
        defaultSleep();
        final By moduleEmotionsWithDiary = By.xpath("//ul[@class='catalogue-cards']/li[2]");
        findElementByLocatorAndClick(moduleEmotionsWithDiary);

        // starting diary module
        final By startDiaryModuleButtonLocator = By.id("form_actions_start");
        findElementByLocatorAndClick(startDiaryModuleButtonLocator);

        // diary steps
        // step 1
        defaultSleep();
        final By moodChoosingIconLocator = By.xpath("//div[@id='content']/form/div[2]/div[2]/div/a[1]");
        findElementByLocatorAndClick(moodChoosingIconLocator);
        // step 2
        final By emotionSliderLocator = By.xpath("//md-legacy-provider/div/div/form/div[3]/div[2]/div");
        scrollToElementWithCorrectionAndClick(emotionSliderLocator, HEADER_HEIGHT_CORRECTION_IN_PIXELS);
        // step 3
        final By bodyFeelingInputLocator = By.id("form.text_3");
        scrollToElementWithCorrectionAndFillInRandomData(bodyFeelingInputLocator, HEADER_HEIGHT_CORRECTION_IN_PIXELS);
        // step 4
        final By currentSituationInputLocator = By.id("form.text_2");
        scrollToElementWithCorrectionAndFillInRandomData(currentSituationInputLocator, HEADER_HEIGHT_CORRECTION_IN_PIXELS);

        // saving diary
        final By saveDiaryButtonLocator = By.id("form_actions_save");
        scrollToElementWithCorrectionAndClick(saveDiaryButtonLocator, HEADER_HEIGHT_CORRECTION_IN_PIXELS);
    }

    private void scrollToContinueButtonAndClick() throws InterruptedException {
        final By continueButtonLocator = By.xpath("//button[@title='Continue']");
        scrollToElementAndClick(continueButtonLocator);
    }
}
