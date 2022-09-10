package com.feign.api.entity.parkingLots;




public class Parking_for_user extends Parking {

    private int available_parking_spaces_num;     //可用车位数量


    public int getAvailable_parking_spaces_num() {
        return available_parking_spaces_num;
    }

    public void setAvailable_parking_spaces_num(int available_parking_spaces_num) {
        this.available_parking_spaces_num = available_parking_spaces_num;
    }


}
