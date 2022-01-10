package sample;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

public class Book {

    Label book_name;
    Label author;
    Label book_description;
    Label price;
    Label stock;
    Label primary_keyword;
    Label secondary_keyword;
    Label rating;
    int y;
    int x = 100;
    public static int columnWidth = 200;

    Button buyButton;

    ResultSet resultSet;


    public Book(ResultSet resultSet, ArrayList<Book> books) {
        this.resultSet=resultSet;
        try {
            this.y = books.get(books.size() - 1).y + 50;
        } catch (Exception e) {
            this.y = 150;
        }
        try {
            this.book_name = new Label(resultSet.getString("book_name"));
            this.author = new Label(resultSet.getString("author_name"));
            this.book_description = new Label(resultSet.getString("book_description"));
            this.price = new Label(String.valueOf(resultSet.getDouble("price")));
            this.stock = new Label(String.valueOf(resultSet.getInt("in_stock")));
            this.primary_keyword = new Label(resultSet.getString("primary_keyword"));
            this.secondary_keyword = new Label(resultSet.getString("secondary_keyword"));
            this.rating = new Label(String.valueOf(resultSet.getDouble("rating")));
        } catch (Exception e) {
            System.out.println("error reading in book");
        }
        buyButton = new Button("Buy");
        buyButton.setOnMouseClicked(event -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src\\password.txt"));//read in pw from a txt file
                String password = br.readLine();
                //
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", password);
                Statement statement = connection.createStatement();
                statement.executeUpdate("update book_details set in_stock = in_stock -1 where book_name='" + this.book_name.getText() + "';");
                //if (a) {
                    this.stock.setText(String.valueOf(Integer.parseInt(this.stock.getText()) - 1));
                //}
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error buying book - Book");
            }
        });
    }

    public void output() {
        System.out.println(book_name);
        System.out.println(author);
        System.out.println(book_description);
        System.out.println(price);
        System.out.println(stock);
        System.out.println(primary_keyword);
        System.out.println(secondary_keyword);
        System.out.println(rating);
        System.out.println("------------------------------");
    }


    public void load(Group group, ArrayList<Book> books) {
        group.getChildren().add(this.book_name);
        this.book_name.relocate(x, y);
        group.getChildren().add(this.author);
        this.author.relocate(x + (columnWidth * 1), y);
        group.getChildren().add(this.book_description);
        this.book_description.relocate(x + (columnWidth * 2), y);
        group.getChildren().add(this.price);
        this.price.relocate(x + (columnWidth * 3), y);
        group.getChildren().add(this.stock);
        this.stock.relocate(x + (columnWidth * 4), y);
        group.getChildren().add(this.primary_keyword);
        this.primary_keyword.relocate(x + (columnWidth * 5), y);
        group.getChildren().add(this.secondary_keyword);
        this.secondary_keyword.relocate(x + (columnWidth * 6), y);
        group.getChildren().add(this.rating);
        this.rating.relocate(x + (columnWidth * 7), y);
        group.getChildren().add(this.buyButton);
        this.buyButton.relocate(x + (columnWidth * 8), y);
    }

    public void unload(Group group) {
        group.getChildren().removeAll(this.book_name, author, book_description, price,
                stock, primary_keyword, secondary_keyword, rating);
    }
}
