package com.avneacsu.sept;

import com.avneacsu.sept.dao.ScalaMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Cosmin on 18/10/2015.
 */
public class Main {

    public static void main (String[] args) {
        ScalaMain.function();

        System.out.println("hello world!");

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM church;" );
            while ( rs.next() ) {
                int id = rs.getInt("church_id");
                String  name = rs.getString("name");
                String  address = rs.getString("address");
                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "ADDRESS = " + address );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("Database not open!");
        }

    }
}
