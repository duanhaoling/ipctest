package com.example.ipctest.annotations;

import java.util.List;

/**
 * Created by ldh on 2016/9/13 0013.
 */
public class PasswordUtils {

    @UserCase(id = 47, description = "Passwords must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UserCase(id = 48)
    public String eccryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UserCase(id = 49,description = "New passwords can't equal previously used ones ")
    public boolean checkForNewPassWord(List<String> prePasswords, String password) {
        return !prePasswords.contains(password);
    }

}