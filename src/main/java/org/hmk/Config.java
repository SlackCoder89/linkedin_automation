package org.hmk;

import java.util.Map;

public class Config {
    private String userName;
    private String password;
    private String jobListUrl;

    public Config(Map<String, Object> map) {
        this.userName = (String) map.get("user-name");
        this.password = (String) map.get("password");
        this.jobListUrl = (String) map.get("job-list-url");
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getJobListUrl() {
        return jobListUrl;
    }

    public void setJobListUrl(String jobListUrl) {
        this.jobListUrl = jobListUrl;
    }
}
