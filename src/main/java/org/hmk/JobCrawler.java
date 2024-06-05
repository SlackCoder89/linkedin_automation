package org.hmk;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class JobCrawler {
    private final WebDriver webDriver;
    private final String jobListUrl;
    private int pageIndex;
    private int indexOfCurrentPage = 0;
    private List<WebElement> jobList = new ArrayList<>();

    public JobCrawler(WebDriver webDriver, String jobListUrl) {
        this.webDriver = webDriver;
        this.jobListUrl = jobListUrl;
    }

    public void start() {
        webDriver.get(jobListUrl);
    }

    public WebElement fetchNextJob() {
        if (indexOfCurrentPage >= jobList.size()) {
            jobList = fetchJobListFromNextPage();
            indexOfCurrentPage = 0;
        }
        return jobList.get(indexOfCurrentPage++);
    }

    private List<WebElement> fetchJobListFromNextPage() {
        if (pageIndex >= 10) {
            throw new RuntimeException("Only a maximum of 10 pages can be crawled");
        }
        List<WebElement> pages = webDriver.findElements(By.className("artdeco-pagination__indicator"));
        try {
            // in case of that last job application is not finished
            Thread.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        pages.get(pageIndex++).click();
        return webDriver.findElements(By.className("jobs-search-results__list-item"));
    }

//    private void fetchJobList() {
//        List<WebElement> elementList = webDriver.findElements(By.className("jobs-search-results__list-item"));
//        for (WebElement ele : elementList) {
//            try {
//                processJob(ele);
//            } catch (StaleElementReferenceException e) {
//                System.err.println(e);
//            }
//        }
//    }

//    private void processJob(WebElement jobEle) {
//        if (jobEle.getText().isEmpty()) {
//            // skip non-loaded element
//            return;
//        }
//        List<WebElement> state = jobEle.findElements(By.className("job-card-container__footer-job-state"));
//        if (!state.isEmpty() && state.get(0).getText().equals("Applied")) {
//            return;
//        } else {
//            jobApplication.apply(jobEle);
//        }
//    }
}
