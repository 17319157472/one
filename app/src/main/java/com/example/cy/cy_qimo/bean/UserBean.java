package com.example.cy.cy_qimo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id(autoincrement = true)
    private long id;
    private String name;
    private String pass;
    private String imagepath;
    @Generated(hash = 439834390)
    public UserBean(long id, String name, String pass, String imagepath) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.imagepath = imagepath;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPass() {
        return this.pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getImagepath() {
        return this.imagepath;
    }
    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
