package com.example.relo.Model;

public class Drivers {
    String name;
    String registration;
    String tel_number;
    String filepath;
    String VehicleType;

    public Drivers() {
    }

    public Drivers(String name, String registration, String tel_number, String filepath, String vehicleType) {
        this.name = name;
        this.registration = registration;
        this.tel_number = tel_number;
        this.filepath = filepath;
        VehicleType = vehicleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }
}
