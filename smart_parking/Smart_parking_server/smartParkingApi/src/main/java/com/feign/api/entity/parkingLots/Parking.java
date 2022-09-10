package com.feign.api.entity.parkingLots;




public class Parking {


    private String parking_lot_name;                 //停车场名
    private String parking_in_the_city;              //停车场所在城市
    private String parking_lot_number;                //停车场编号
    private int parking_spaces_num;                 //车位数量
    private float billing_rules;                   //计费规则 (元/小时)
    private String longitude;                      //经度
    private String latitude;                      //纬度


    public String getParking_lot_name() {
        return parking_lot_name;
    }

    public void setParking_lot_name(String parking_lot_name) {
        this.parking_lot_name = parking_lot_name;
    }

    public String getParking_in_the_city() {
        return parking_in_the_city;
    }

    public void setParking_in_the_city(String parking_in_the_city) {
        this.parking_in_the_city = parking_in_the_city;
    }

    public String getParking_lot_number() {
        return parking_lot_number;
    }

    public void setParking_lot_number(String parking_lot_number) {
        this.parking_lot_number = parking_lot_number;
    }

    public int getParking_spaces_num() {
        return parking_spaces_num;
    }

    public void setParking_spaces_num(int parking_spaces_num) {
        this.parking_spaces_num = parking_spaces_num;
    }

    public float getBilling_rules() {
        return billing_rules;
    }

    public void setBilling_rules(float billing_rules) {
        this.billing_rules = billing_rules;
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

    public Parking(String parking_lot_name, String parking_in_the_city, String parking_lot_number, int parking_spaces_num, float billing_rules, String longitude, String latitude) {
        this.parking_lot_name = parking_lot_name;
        this.parking_in_the_city = parking_in_the_city;
        this.parking_lot_number = parking_lot_number;
        this.parking_spaces_num = parking_spaces_num;
        this.billing_rules = billing_rules;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Parking() {
    }

    @Override
    public String toString() {
        return "Parking{" +
                "parking_lot_name='" + parking_lot_name + '\'' +
                ", parking_in_the_city='" + parking_in_the_city + '\'' +
                ", parking_lot_number='" + parking_lot_number + '\'' +
                ", parking_spaces_num=" + parking_spaces_num +
                ", billing_rules=" + billing_rules +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
