package com.licenta.dao.beans;

import java.io.Serializable;

/**
 * @author Lucian CONDESCU
 */
public class UserBean implements Serializable {
    private String id;
    private String name;
    private String email;

    public UserBean(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
