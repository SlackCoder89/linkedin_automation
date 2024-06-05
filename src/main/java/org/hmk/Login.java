package org.hmk;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
    private final WebDriver webDriver;
    private final String loginUrl;
    private final String userName;
    private final String password;

    public Login(WebDriver webDriver, String loginUrl, String userName, String password) {
        this.webDriver = webDriver;
        this.loginUrl = loginUrl;
        this.userName = userName;
        this.password = password;
    }

    public void login() {
        webDriver.get(loginUrl);
        WebElement loginLinkButton = webDriver.findElement(By.className("nav__button-secondary"));
        loginLinkButton.click();

        WebElement userNameEle = webDriver.findElement(By.id("username"));
        userNameEle.sendKeys(userName);
        WebElement passwordEle = webDriver.findElement(By.id("password"));
        passwordEle.sendKeys(password);
        WebElement loginButton = webDriver.findElement(By.className("login__form_action_container"));
        loginButton.click();
    }
}
