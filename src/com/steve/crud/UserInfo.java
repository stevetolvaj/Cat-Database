package com.steve.crud;

import java.util.UUID;

/**
 * The UserInfo class will be used to create user objects to store user UUID and names.
 */

public class UserInfo {
    private static UUID id;
    private static String userName;

    UserInfo(UUID id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        UserInfo.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        UserInfo.userName = userName;
    }

}
