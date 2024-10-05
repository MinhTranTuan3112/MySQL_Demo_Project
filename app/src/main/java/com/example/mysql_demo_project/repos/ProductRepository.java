package com.example.mysql_demo_project.repos;

import android.util.Log;

import com.example.mysql_demo_project.DbUtils;
import com.example.mysql_demo_project.Product;
import com.example.mysql_demo_project.interfaces.IProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ProductRepository implements IProductRepository {

    @Override
    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DbUtils.makeConnection()) {

            String query = "select * from product";

            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");

                    products.add(new Product(id, name, price));
                }
            }


        } catch (Exception ex) {
            Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
        }

        return products;
    }
}
