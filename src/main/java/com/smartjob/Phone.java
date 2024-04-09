package com.smartjob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private String citycode;
    private String contrycode;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }


    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }


    public String getContrycode() {
        return contrycode;
    }


    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }


    public void setPhones(String number, String citycode, String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }

}

