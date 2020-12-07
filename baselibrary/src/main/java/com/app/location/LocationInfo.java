package com.app.location;


import java.io.Serializable;

public class LocationInfo implements Serializable {
    //返回定位信息
    String province;//省
    String city;//市
    String district;//区
    String longitude;//经度
    String latitude;//纬度
    String cityCode;//城市编码
    String desc;//具体信息
    boolean isSuccess = false;//是否定位成功

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public LocationInfo() {
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", desc='" + desc + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
