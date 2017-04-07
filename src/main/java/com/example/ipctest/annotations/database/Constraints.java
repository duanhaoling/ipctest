package com.example.ipctest.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ldh on 2016/9/14 0014.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    boolean primaryKey() default false; //当前域变量是否为主键
    boolean allownull() default true;//当前与变量是否可以为null
    boolean unique() default false;//当前域变量是否独一无二
}
