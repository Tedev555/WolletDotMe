package com.thanongsine.wolletdotme.Dao;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ted555 on 4/7/17.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String sex;

    public User() {

    }

    public User(String username, String password, String sex) {
        this.username = username;
        this.email = password;
        this.sex = sex;
    }

}
