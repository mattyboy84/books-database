package sample;

import javafx.scene.Group;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class bookStore {

    ArrayList<Book> books = new ArrayList<>();

    Button[] buttons = new Button[8];
    //
    String order = "asc";


    public bookStore(Group group) {
        buttons[0] = new Button("Sort by: " + "book name");
        buttons[1] = new Button("Sort by: " + "author name");
        buttons[2] = new Button("Sort by: " + "book description");
        buttons[3] = new Button("Sort by: " + "price");
        buttons[4] = new Button("Sort by: " + "stock");
        buttons[5] = new Button("Sort by: " + "primary keyword");
        buttons[6] = new Button("Sort by: " + "secondary keyword");
        buttons[7] = new Button("Sort by: " + "rating");


        collectBooks(group, "select * from book_details");

        buttons[0].setOnMouseClicked(event -> reOrder(group, "book_name"));
        buttons[1].setOnMouseClicked(event -> reOrder(group, "author_name"));
        buttons[2].setOnMouseClicked(event -> reOrder(group, "book_description"));
        buttons[3].setOnMouseClicked(event -> reOrder(group, "price"));
        buttons[4].setOnMouseClicked(event -> reOrder(group, "in_stock"));
        buttons[5].setOnMouseClicked(event -> reOrder(group, "primary_keyword"));
        buttons[6].setOnMouseClicked(event -> reOrder(group, "secondary_keyword"));
        buttons[7].setOnMouseClicked(event -> reOrder(group, "rating"));

        //for (int i = 0; i <books.size() ; i++) {
        //    //System.out.println(books.get(i).book_name.toString());
        //}
    }

    private void reOrder(Group group, String field) {
        collectBooks(group, "select * from book_details ORDER BY " + field + " " + this.order);
        if (this.order.equals("asc")) {
            this.order = "desc";
        } else {
            this.order = "asc";
        }
    }

    private void collectBooks(Group group, String query) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\password.txt"));//read in pw from a txt file
            String password = br.readLine();
            //
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            for (Book book : books) {
                book.unload(group);
            }
            for (Button button : buttons) {
                group.getChildren().remove(button);
            }
            books.clear();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString("book_name"));

                books.add(new Book(resultSet, books));
                //books.get(books.size() - 1).output();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        load(group);
    }

    public void load(Group group) {
        for (int i = 0; i < books.size(); i++) {
            books.get(i).load(group, books);
        }
        int x = 100;
        for (Button button : buttons) {
            group.getChildren().add(button);
            button.relocate(x, 100);
            x += Book.columnWidth;
        }

    }
}
