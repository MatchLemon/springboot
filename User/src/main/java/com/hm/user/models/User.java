package com.hm.user.models;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection="user")
public class User implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;
    private String name;
    private String passwd;
    private String phone;
    private String addr;

    public User(String name, String passwd, String phone, String addr) {

        this.name = name;
        this.passwd = passwd;
        this.phone = phone;
        this.addr = addr;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(ObjectId id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + getId() +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
