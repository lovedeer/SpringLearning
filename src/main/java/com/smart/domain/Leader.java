package com.smart.domain;

public class Leader {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void greet() {
        System.out.println("greeting");
    }
    public void service() {
        System.out.println("service");
    }
}
