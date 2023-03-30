package com.example.relo.Model;

public class RegisterModel {
    String Email;
    String Phone;
    String Names;

    public RegisterModel() {
    }

    public RegisterModel(String email, String phone, String names) {
        Email = email;
        Phone = phone;
        Names = names;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }
}
