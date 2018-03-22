package com.hm.user.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Document(collection = "user")
@JsonIgnoreProperties(value = {"passwd"})
public class User implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull
    private String name;
    @NotNull
    //@JsonIgnore
    private String passwd;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private String addr;

    public User() {
    }

    public User(String name, String passwd, String phone, String addr) {

        this.name = name;
        this.passwd = passwd;
        this.phone = phone;
        this.addr = addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
