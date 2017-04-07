package com.example.ipctest.annotations.database;

/**
 * Created by ldh on 2016/9/14 0014.
 */
@DBTable(name = "MEMBER")
public class Member {
    @SQLString(30)
    String firstName;
    @SQLString(50)
    String lastName;
    @SQLInteger
    Integer age;
    @SQLString(value = 30, constraints = @Constraints(primaryKey = true))
    String handler;
    static int memberCount;

    public String getHandler() {
        return handler;
    }

    public Integer getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String toString() {
        return handler;
    }
}
