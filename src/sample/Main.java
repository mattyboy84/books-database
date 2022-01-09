package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Main extends Application {

    Group group = new Group();

    Scene scene=new Scene(group,500,500);
    ArrayList<Book> books = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception{
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\password.txt"));//read in pw from a txt file
            String password = br.readLine();
            //
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root",password);
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from book_details");

            while (resultSet.next()){
                //System.out.println(resultSet.getString("book_name"));
                books.add(new Book(resultSet));
                books.get(books.size()-1).output();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }







        //stage.setScene(scene);
        //stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
