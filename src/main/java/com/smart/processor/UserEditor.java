package com.smart.processor;

import java.beans.PropertyEditorSupport;

public class UserEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.indexOf(",") < 0) {
            throw new IllegalArgumentException("invalid formation of argument");
        }
        String[] arg = text.split(",");
        User user = new User();
        user.setUserName(arg[0]);
        user.setUserId(Integer.valueOf(arg[1]));
        setValue(user);
    }
}
