package model;

public class RegisterObject {
    private String username;
    private String orgName;
    private String password;

    public RegisterObject() {
    }

    public RegisterObject(String username, String orgName, String password) {
        this.username = username;
        this.orgName = orgName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
