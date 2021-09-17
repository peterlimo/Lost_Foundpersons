package com.example.lost_foundpersons.data;

public class FoundData {
        String name,location,age,gender,status;

        public FoundData(String name, String location, String age, String gender,String status)
        {
            this.name = name;
            this.location = location;
            this.age = age;
            this.status=status;
            this.gender = gender;
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
}
