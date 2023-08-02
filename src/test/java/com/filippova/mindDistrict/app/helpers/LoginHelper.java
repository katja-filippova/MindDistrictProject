package com.filippova.mindDistrict.app.helpers;

import com.filippova.mindDistrict.entity.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginHelper extends BaseHelper {

    public LoginHelper(WebDriver wd) {
        super(wd);
    }

    public void fillInLoginFormAndClickSubmitButton(User user) throws InterruptedException {
        fillInInputData(By.id("login"), user.getEmail());
        fillInInputData(By.id("password"), user.getPassword());

        findElementByLocatorAndClick(By.xpath("//button[text()=\"Log in\"]"));
    }
}
