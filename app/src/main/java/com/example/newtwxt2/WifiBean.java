package com.example.newtwxt2;

public class WifiBean {
    private String wifiname;
    private String wifipassword;
    private String capabilities;

    @Override
    public String toString() {
        return "WifiBean{" +
                "wifiname='" + wifiname + '\'' +
                ", wifipassword='" + wifipassword + '\'' +
                ", capabilities='" + capabilities + '\'' +
                '}';
    }

    public String getWifiname() {
        return wifiname;
    }

    public void setWifiname(String wifiname) {
        this.wifiname = wifiname;
    }

    public String getWifipassword() {
        return wifipassword;
    }

    public void setWifipassword(String wifipassword) {
        this.wifipassword = wifipassword;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }
}
