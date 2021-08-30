package com.example.newtwxt2;

public class Status {
    private String ssid;
    private int level;
    private String State;
    private String capabilities;

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Status{" +
                "ssid='" + ssid + '\'' +
                ", level=" + level +
                ", State='" + State + '\'' +
                ", capabilities='" + capabilities + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public int getLevel() {
        return level;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public String getCapabilities() {
        return capabilities;
    }
    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

}
