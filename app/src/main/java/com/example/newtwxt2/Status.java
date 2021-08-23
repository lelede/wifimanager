package com.example.newtwxt2;

public class Status {
    private String ssid;
    private int level;
    private String State;
    private String capabilities;

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

    @Override
    public String toString() {
        return "Status{" +
                "ssid='" + ssid + '\'' +
                ", level=" + level +
                ", capabilities='" + capabilities + '\'' +
                '}';
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
