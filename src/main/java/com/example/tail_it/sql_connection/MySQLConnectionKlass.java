package com.example.tail_it.sql_connection;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectionKlass {
    public static Connection doConnect() {
        Connection con = null;
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/tail_it", "root", "Admin123");
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return con;
    }
//    public static void main(String ary[])
//    {
//        if(doConnect()==null)
//            System.out.println("Sorryyyy");
//        else
//            System.out.println("Badhaiiii");
//
//    }
}
