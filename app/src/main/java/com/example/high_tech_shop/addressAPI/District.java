package com.example.high_tech_shop.addressAPI;

public class District {
    private Integer DistrictID;
    private String DistrictName;
    private Integer ProvinceID;
    private  String Code;
    private Integer Type;
    private Integer SupportType;

    public Integer getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(Integer districtID) {
        DistrictID = districtID;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public Integer getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(Integer provinceID) {
        ProvinceID = provinceID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getSupportType() {
        return SupportType;
    }

    public void setSupportType(Integer supportType) {
        SupportType = supportType;
    }
}