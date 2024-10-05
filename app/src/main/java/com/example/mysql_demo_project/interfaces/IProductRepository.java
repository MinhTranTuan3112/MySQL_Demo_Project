package com.example.mysql_demo_project.interfaces;

import com.example.mysql_demo_project.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IProductRepository {
    ArrayList<Product> getProducts() throws SQLException;
}
