package com.example.high_tech_shop.addressAPI;

public class Ward {
    private Integer WardID;
    private String WardName;
    private Integer DistrictID;

    public Integer getWardID() {
        return WardID;
    }

    public void setWardID(Integer wardID) {
        WardID = wardID;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public Integer getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(Integer districtID) {
        DistrictID = districtID;
    }
}