package org.hmk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class Automation {
    String LOG_IN_URL = "https://www.linkedin.com/";
    public void start(Config config) {
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        new Login(webDriver, LOG_IN_URL, config.getUserName(), config.getPassword()).login();
        JobCrawler crawler = new JobCrawler(webDriver, config.getJobListUrl());
        crawler.start();
        JobApplication application = new JobApplication(webDriver);
        while (true) {
            WebElement job = crawler.fetchNextJob();
            application.apply(job);
        }
    }

    /*
    Debug with the existing Chrome
     */
    public void startDebug(Config config) {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        RemoteWebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        JobCrawler crawler = new JobCrawler(driver, config.getJobListUrl());
        crawler.start();
        JobApplication application = new JobApplication(driver);
        while (true) {
            WebElement job = crawler.fetchNextJob();
            application.apply(job);
        }
    }
}
