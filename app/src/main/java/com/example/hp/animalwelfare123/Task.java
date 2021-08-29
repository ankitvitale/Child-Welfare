package com.example.hp.animalwelfare123;

public class Task {

    public String name,location,mobile,image;
    public Task() {
    }

    public Task(String name, String location, String mobile, String image) {
        this.name = name;
        this.location = location;
        this.mobile = mobile;
        this.image = image;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
