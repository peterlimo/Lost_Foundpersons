package com.example.lost_foundpersons.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class MissData {
    String name,location,age,gender,status,url,user,isAlive;
   @ServerTimestamp
   Timestamp time=null;
    public MissData(String name, String location, String age, String gender, String status, String url, String user, String isAlive,Timestamp time) {
        this.name = name;
        this.location = location;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.url = url;
        this.user = user;
        this.isAlive = isAlive;
        this.time=time;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
