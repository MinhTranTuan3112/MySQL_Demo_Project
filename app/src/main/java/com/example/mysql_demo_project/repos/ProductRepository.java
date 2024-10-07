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
        Connection conn = null;

        try {
            conn = DbUtils.makeConnection();
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
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }

        return products;
    }
    @Override
    public void createProduct(String name, double price) throws SQLException {
        Connection conn = null;
        try {
            conn = DbUtils.makeConnection();
            String query = "INSERT INTO product (name, price) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, price);

            ps.executeUpdate();

        } catch (Exception ex) {
            Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }
    @Override
    public void deleteProduct(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = DbUtils.makeConnection();
            String query = "delete from product where id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception ex) {
            Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    @Override
    public void updateProduct(int id, String name, double price) throws SQLException {
        Connection connection = null;
        try {
            connection = DbUtils.makeConnection();
            String query = "UPDATE product SET name = ?, price = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
        catch (Exception ex) {
            Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));
        }
        finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}
