package com.steve.crud;

/**
 * The UserHolder class is used as temporary storage to keep user details until the main cats screen is loaded.
 * The user UUID and name will be used to only allow changes made by the same user to the database.
 */

public final class UserHolder {
    private UserInfo user;
    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder() {  };

    public static UserHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getUser() {
        return user;
    }
}
