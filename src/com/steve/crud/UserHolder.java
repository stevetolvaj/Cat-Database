package com.steve.crud;

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
