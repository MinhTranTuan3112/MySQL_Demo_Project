package com.example.mysql_demo_project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {
    public static Connection makeConnection() throws Exception {
        Connection cn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo_prm_db", "root", "12345");
        return cn;
    }
}
