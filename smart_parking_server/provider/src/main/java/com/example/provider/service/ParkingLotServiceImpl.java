package com.example.provider.service;

import com.example.provider.entiry.Parking_lot_information;
import com.example.provider.entiry.User;
import com.example.provider.service.base.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired(required = false)
    private Parking_lot_information parkingLotInformation;


    @Override
    public String add_Parking(String user_name, String password, String user_id) {
        return null;
    }

    @Override
    public String login_Parking(String user_name, String password) {
        return null;
    }

    @Override
    public User find_Parking(String user_name) {
        return null;
    }

    @Override
    public Integer getAllParkingNumber() {
        return null;
    }

    @Override
    public List<User> getAllParking() {
        return null;
    }

    @Override
    public void deleteAllParking() {

    }
}
