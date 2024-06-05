package org.hmk;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class JobApplication {
    private final WebDriver webDriver;

    public JobApplication(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void apply(WebElement jobEle) {
        try {
            doApply(jobEle);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void doApply(WebElement jobEle) {
        if (!isValid(jobEle) || isApplied(jobEle)) {
            return;
        }

        jobEle.click();
        WebElement applyButton;
        try {
            applyButton = webDriver.findElement(By.className("jobs-apply-button"));
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
        applyButton.click();
        try {
            // wait for page loading
            Thread.sleep(Duration.ofSeconds(4));
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        while (true) {
            try {
                WebElement nextButton = webDriver.findElement(By.className("artdeco-button--primary"));
                nextButton.click();
            } catch (Exception e) {
                System.err.println(e);
                closeCompletePrompt(webDriver);
                break;
            }
            if (hasError()) {
                closePrompt();
                break;
            }
        }
    }

    private boolean isValid(WebElement jobEle) {
        String text;
        try {
            text = jobEle.getText();
        } catch (StaleElementReferenceException e) {
            System.err.println(e);
            return false;
        }
        return text != null && !text.isEmpty();
    }

    private boolean isApplied(WebElement jobEle) {
        List<WebElement> state = jobEle.findElements(By.className("job-card-container__footer-job-state"));
        return !state.isEmpty() && state.get(0).getText().equals("Applied");
    }

    private void closeCompletePrompt(WebDriver driver) {
        try {
            List<WebElement> closeButton = driver.findElements(By.className("artdeco-button--muted"));
            if (closeButton.isEmpty()) {
                throw new RuntimeException("Can't find close button");
            }
            closeButton.get(0).click();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void closePrompt() {
        List<WebElement> closeButton = webDriver.findElements(By.className("artdeco-button--muted"));
        if (closeButton.isEmpty()) {
            throw new RuntimeException("Can't find close button");
        }
        closeButton.get(0).click();
        List<WebElement> discardButton = webDriver.findElements(By.cssSelector("button[data-control-name=\"discard_application_confirm_btn\"]"));
        if (discardButton.isEmpty()) {
            return;
        }
        discardButton.get(0).click();
    }

    private boolean hasError() {
        List<WebElement> errors = webDriver.findElements(By.className("artdeco-inline-feedback--error"));
        return !errors.isEmpty();
    }
}
