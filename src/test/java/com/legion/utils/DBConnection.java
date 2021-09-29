package com.legion.utils;

import java.sql.*;

public class DBConnection {
    public static void updateDB(String sql) {
        Connection con;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://rds.release.legion.local:3306";
        //String url = "jdbc:mysql://dev-eks-shared-rc-mysql8.cijomzi1o1vu.us-west-2.rds.amazonaws.com:3306";
        String user = "legion";
        String password = "legionwork";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("------Connect DB successfully!------");
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            if(result>0){
                System.out.println("-------Update DB successfully----------" + "\t");
            }else {
                System.out.println("-------Update operation failed----------" + "\t");
            }

            statement.close();
            con.close();
            System.out.println("-------Disconnect DB successfully----------" + "\t");
        } catch (ClassNotFoundException e) {
            //DB driver exception
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //connection exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static String queryDB(String table, String columnLabel, String condition) {
        Connection con;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://rds.release.legion.local:3306";
        //String url = "jdbc:mysql://rds.shared.legion.local:3306";
        String user = "legion";
        String password = "legionwork";
        String value = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("------Connect DB successfully!------");
            Statement statement = con.createStatement();
            String sql = "Select " + columnLabel + " from " + table + " where " + condition;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                value = rs.getString(columnLabel);
                System.out.println(columnLabel + ": " + value + "\t");
                System.out.println("--------Query successfully---------");
            }
            rs.close();
            statement.close();
            con.close();
            System.out.println("-------Disconnect DB successfully----------" + "\t");
        } catch (ClassNotFoundException e) {
            //DB driver exception
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //connection exception
            e.printStackTrace();
            System.out.println("Sorry,failed to execute the sql!");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return value;
    }

    public static void main(String[] args) {
        //"select username from legiondb.UserRevEntity where id='776'";

        String sql1 = "INSERT INTO legiondb.UserRevEntity VALUES ('1596','1482278609925','SYSTEM_newCreated')";
        updateDB(sql1);
        queryDB("legiondb.UserRevEntity", "username", "id='1596'");
        String sql2 = "Update legiondb.UserRevEntity set username = 'SYSTEM_USER_update1596' where id = 1596";
        updateDB(sql2);
        queryDB("legiondb.UserRevEntity", "username", "id='1596'");
        String sql3 = "Delete from legiondb.UserRevEntity where id = 1596";
        updateDB(sql3);


        //SQL
        //INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);
        //INSERT INTO table_name VALUES (value1, value2, value3, ...);
        //update a filed value
        //UPDATE Customers SET ContactName = 'Alfred Schmidt', City= 'Frankfurt' WHERE CustomerID = 1;
        //String sql3 = "Update legiondb.UserRevEntity set username = 'SYSTEM_USER_update' where id = 776";
        //Delete from DB
        //Delete from legiondb.UserRevEntity where id = 776
        /*queryDB("legiondb.UserRevEntity", "username", "id='1596'");*/

    }


}
