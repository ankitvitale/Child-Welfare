package com.example.hp.animalwelfare123;

public class TaskDoctor {

    public String name, add, image, mobile;
    public TaskDoctor() {
    }

    public TaskDoctor(String name, String add, String image, String mobile) {
        this.name = name;
        this.add = add;
        this.image = image;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
