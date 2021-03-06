package com.company;


import java.sql.*;
import java.util.Scanner;


public class Booking {

    public String operation = "";
    public Statement statement;
    public String error = "Error № 666";


    public static void main(String[] args) {

        Booking program = new Booking();
        program.open();

    }

    Connection connection;


    boolean open() {


        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:C:\\Users\\Sonikpalms\\IdeaProjects\\Basebooks\\src\\com\\company\\sqlite\\books.db");
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

            Class.forName("org.sqlite.JDBC");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book name_new: ");
            String name_new = scanner.nextLine();
            System.out.print("Enter book name_old: ");
            String name_old = scanner.nextLine();


            statement = connection.createStatement();
            String query = "Update books set name ='" + name_new + "'WHERE name='" + name_old + "' ";
            int rs = statement.executeUpdate(query);


            if (rs == 0) {
                System.out.println(error + " | " + "book name = " + name_old + " | " + "not found in database");

                statement.close();
                connection.close();

            } else {
                System.out.println("Book was Update");

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

            statement = connection.createStatement();
            String querry = "DELETE from books WHERE name ='" + name_book + "' ";
            int rs = statement.executeUpdate(querry);


            if (rs == 0) {
                System.out.println(error + " | " + "book name = " + name_book + " | " + "not found in database");

                statement.close();
                connection.close();

            } else {
                System.out.println("Book was removed");

                statement.close();
                connection.close();
            }
        } catch (Exception e)

        {

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

            statement = connection.createStatement();
            statement.executeUpdate(querry);

            System.out.println("Book was added");
            statement.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    void select() {
        try {
            statement = connection.createStatement();
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
