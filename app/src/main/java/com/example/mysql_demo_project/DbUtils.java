package com.example.mysql_demo_project;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DbUtils {
    public static Connection makeConnection(){
        Connection cn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://10.0.2.2:3306/demo_prm_db";
            cn =  DriverManager.getConnection(connectionString,"root","12345");
        }catch (Exception e){
            Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
        }
        return  cn;
    }
}
