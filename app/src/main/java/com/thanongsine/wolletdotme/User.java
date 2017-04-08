package com.thanongsine.wolletdotme;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ted555 on 4/7/17.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.email = password;
    }

}
