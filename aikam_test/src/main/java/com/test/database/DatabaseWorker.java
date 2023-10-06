package com.test.database;

import com.test.response.ErrorResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseWorker {
    private static String url = "jdbc:postgresql://localhost:5433/aikam_shop";
    private static String username = "postgres";
    private static String password = "123";

    public static List<Object[]> searchByLastName(String lastName) {
        List<Object[]> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from searchByLastNameFunction(?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, lastName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String resultLastName = resultSet.getString("last_name");
                String resultFirstName = resultSet.getString("first_name");
                Object[] rowData = {"lastName", resultLastName, "firstName", resultFirstName};
                result.add(rowData);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new ErrorResponse(e.getMessage());
        }
        return result;
    }

    public static List<Object[]> searchByProductsAndCount(String products, int minTimes) {
        List<Object[]> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from searchByProductsAndCountFunction(?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, products);
            statement.setInt(2, minTimes);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String resultLastName = resultSet.getString("last_name");
                String resultFirstName = resultSet.getString("first_name");
                Object[] rowData = {"lastName", resultLastName, "firstName", resultFirstName};
                result.add(rowData);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new ErrorResponse(e.getMessage());
        }
        return result;
    }

    public static List<Object[]> searchByMinAndMaxExpenses(double minExpenses, double maxExpenses) {
        List<Object[]> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from searchByMinAndMaxExpenses(?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDouble(1, minExpenses);
            statement.setDouble(2, maxExpenses);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String resultLastName = resultSet.getString("last_name");
                String resultFirstName = resultSet.getString("first_name");
                Object[] rowData = {"lastName", resultLastName, "firstName", resultFirstName};
                result.add(rowData);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new ErrorResponse(e.getMessage());
        }
        return result;
    }
    public static List<Object[]> searchByBadCustomers(int badCustomers) {
        List<Object[]> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from searchByBadCustomersFunction(?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, badCustomers);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String resultLastName = resultSet.getString("last_name");
                String resultFirstName = resultSet.getString("first_name");
                Object[] rowData = {"lastName", resultLastName, "firstName", resultFirstName};
                result.add(rowData);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new ErrorResponse(e.getMessage());
        }
        return result;
    }
    public static List<Object[]> stat(Date startDate, Date endDate) {
        List<Object[]> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from statFunction(?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String resultLastName = resultSet.getString("last_name");
                String resultFirstName = resultSet.getString("first_name");
                String resultProductName = resultSet.getString("product_name");
                double resultExpenses = resultSet.getDouble("price");

                Object[] rowData = {"name", resultLastName + " " + resultFirstName, "name", resultProductName, "expenses", resultExpenses};
                result.add(rowData);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new ErrorResponse(e.getMessage());
        }
        return result;
    }
}
