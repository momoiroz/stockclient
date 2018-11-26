package com.example.org1.stockex;

public class RegisterObject {
    private String username;
    private String orgName;

    public RegisterObject() {
    }

    public RegisterObject(String username, String orgName) {

        this.username = username;
        this.orgName = orgName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
