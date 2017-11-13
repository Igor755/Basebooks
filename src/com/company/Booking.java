package com.company;


import java.sql.*;
import java.util.Scanner;


public class Booking {

    public String operation = "";


    public static void main(String[] args) {

        Booking program = new Booking();
        program.open();

    }

    Connection connection;


    boolean open() {


        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:C:\\Users\\i.metlin\\IdeaProjects\\Basebooks\\src\\com\\company\\sqlite\\books.db");
            System.out.println("CONNECT BASE SUCESSFULLY");
            System.out.println("Please Enter operation: add, delete, edit, all");
            Scanner scanner = new Scanner(System.in);
            operation = scanner.nextLine();


            switch (operation) {
                case "add":
                    insert();
                    close();
                    break;

                case "delete":
                    remove();
                    close();
                    break;

                case "edit":
                    edit();
                    close();
                    break;

                case "all":
                    select();
                    close();
                    break;

                default:
                    System.out.println("It is not an internal command");
                    close();
                    break;
            }


            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void edit() {
        try {

            /*

            ResultSet rs = statement.executeQuery("SELECT * FROM books WHERE name IN ('" + name_old + "')");
            if (rs.wasNull() == true) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String author = rs.getString("author");
                    String name = rs.getString("name");
                    System.out.println(id + "\t| " + author + "\t| " + name);
                    System.out.println("Operation done successfully");
                    rs.close();
                    statement.close();
                    connection.close();
                }
            } else {

                Statement statementnew = connection.createStatement();
                String sql = "Update books set name ='" + name_new + "'WHERE name='" + name_old + "' ";
                //PreparedStatement statementnew = connection.prepareStatement(sql);
                statementnew.executeUpdate(sql);

                System.out.println("Book was Update");
                rs.close();
                statement.close();
                connection.close();
            }*/

            Statement statement = connection.createStatement();
            Class.forName("org.sqlite.JDBC");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book name_new: ");
            String name_new = scanner.nextLine();
            System.out.print("Enter book name_old: ");
            String name_old = scanner.nextLine();

            ResultSet rs = statement.executeQuery("Update books set name ='" + name_new + "'WHERE name='" + name_old + "' ");
            if (rs.wasNull() == false) {
                System.out.println("Operation done successfully");
                rs.close();
                statement.close();
                connection.close();

            } else {
                System.out.println("Book was Update");
                rs.close();
                statement.close();
                connection.close();
            }
        } catch (Exception e)

        {

                System.out.println(e.getMessage());


        }

    }

    private void remove() {
        try {


            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book name: ");
            String name_book = scanner.nextLine();


            String querry = "DELETE from books WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(querry);

            statement.setString(1, name_book);
            statement.executeUpdate();

            System.out.println("Book was removed");
            statement.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    void insert() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter author name: ");
            String author = scanner.nextLine();
            System.out.print("Enter books name: ");
            String name = scanner.nextLine();

            String querry = "INSERT INTO books (author, name) " +
                    "VALUES ('" + author + "', '" + name + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(querry);

            System.out.println("Book was added");
            statement.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    void select() {
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT id, author, name " +
                            " FROM books " +
                            " ORDER BY author";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String author = rs.getString("author");
                String name = rs.getString("name");
                System.out.println(id + "\t| " + author + "\t| " + name);
            }
            rs.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
