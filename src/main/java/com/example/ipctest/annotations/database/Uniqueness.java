package com.example.ipctest.annotations.database;

/**
 * Created by ldh on 2016/9/14 0014.
 */

public @interface Uniqueness {
    Constraints contraints() default @Constraints(unique = true);
}
