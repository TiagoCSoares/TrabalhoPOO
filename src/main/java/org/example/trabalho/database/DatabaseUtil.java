package org.example.trabalho.database;
import org.example.trabalho.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "products";
    private static final String FULL_URL = URL + DB_NAME;
    private static final String USER = "admin";
    private static final String PASSWORD = "admin123";

    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FULL_URL, USER, PASSWORD);
    }

    public static void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS product (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "shortDescription VARCHAR(255), " +
                "brand VARCHAR(255), " +
                "category VARCHAR(255), " +
                "listPrice DOUBLE NOT NULL, " +
                "cost DOUBLE" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createProduct(Product product) {
        String insertSQL = "INSERT INTO product (name, shortDescription, brand, category, listPrice, cost) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getShortDescription());
            pstmt.setString(3, product.getBrand());
            pstmt.setString(4, product.getCategory());
            pstmt.setDouble(5, product.getListPrice());
            pstmt.setDouble(6, product.getCost());

            pstmt.executeUpdate();
            System.out.println("Product inserted successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(Product product) {
        String updateSQL = "UPDATE product SET name = ?, shortDescription = ?, brand = ?, category = ?, listPrice = ?, cost = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getShortDescription());
            pstmt.setString(3, product.getBrand());
            pstmt.setString(4, product.getCategory());
            pstmt.setDouble(5, product.getListPrice());
            pstmt.setDouble(6, product.getCost());
            pstmt.setInt(7, product.getId());

            pstmt.executeUpdate();
            System.out.println("Product updated successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(Product product) {
        String deleteSQL = "DELETE FROM product WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, product.getId());

            pstmt.executeUpdate();
            System.out.println("Product deleted successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String shortDescription = rs.getString("shortDescription");
                String brand = rs.getString("brand");
                String category = rs.getString("category");
                double listPrice = rs.getDouble("listPrice");
                double cost = rs.getDouble("cost");

                Product product = new Product(id, name, shortDescription, brand, category, listPrice, cost);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }*/

    public List<Product> getByName(String nameSearching) {
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                if (!rs.getString("name").contains(nameSearching)) {
                    continue;
                }
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String shortDescription = rs.getString("shortDescription");
                String brand = rs.getString("brand");
                String category = rs.getString("category");
                double listPrice = rs.getDouble("listPrice");
                double cost = rs.getDouble("cost");

                Product product = new Product(id, name, shortDescription, brand, category, listPrice, cost);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public int getTotalProductsCount() {
        int total = 0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM product");
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return total;
        }
    }

    public List<Product> getProductsByRange(int startIndex, int endIndex) {
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM product LIMIT ?, ?");
        ) {

            pstmt.setInt(1, startIndex);
            pstmt.setInt(2, endIndex);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String shortDescription = rs.getString("shortDescription");
                    String brand = rs.getString("brand");
                    String category = rs.getString("category");
                    double listPrice = rs.getDouble("listPrice");
                    double cost = rs.getDouble("cost");

                    Product product = new Product(id, name, shortDescription, brand, category, listPrice, cost);
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
