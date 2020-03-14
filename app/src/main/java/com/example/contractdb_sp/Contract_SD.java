package com.example.contractdb_sp;

public class Contract_SD {

    private int id;
    private String name;
    private String location;
    private String phone;

    public Contract_SD() {
    }

    public Contract_SD(String name, String location, String phone) {
        this.name = name;
        this.location = location;
        this.phone = phone;
    }

    public Contract_SD(int id, String name, String location, String phone) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
