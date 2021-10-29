package com.example.lost_foundpersons.data;

public class Match {
    String name,location,age,gender,status,url,user,isAlive,id;
          Boolean  type;

    public Match(String name, String location, String age, String gender, String status, String url, String user, String isAlive,String id,Boolean type) {
        this.name = name;
        this.location = location;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.url = url;
        this.user = user;
        this.isAlive = isAlive;
        this.id=id;
        this.type=type;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(String isAlive) {
        this.isAlive = isAlive;
    }
}