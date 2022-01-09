package sample;

import java.sql.ResultSet;

public class Book {

    String book_name;
    String author;
    String book_description;
    int price;
    int stock;
    String primary_keyword;
    String secondary_keyword;
    double rating;


    public Book(ResultSet resultSet) {
        try {
            this.book_name = resultSet.getString("book_name");
            this.author = resultSet.getString("author_name");
            this.book_description = resultSet.getString("book_description");
            this.price = resultSet.getInt("price");
            this.stock = resultSet.getInt("in_stock");
            this.primary_keyword = resultSet.getString("primary_keyword");
            this.secondary_keyword = resultSet.getString("secondary_keyword");
            this.rating = resultSet.getDouble("rating");
        } catch (Exception e) {
            System.out.println("error reading in book");
        }
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

}
