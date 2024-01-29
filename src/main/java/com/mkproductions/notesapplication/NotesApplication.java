package com.mkproductions.notesapplication;

import com.mkproductions.notesapplication.databases.NotesDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NotesApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/home_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        NotesDatabase.getDatabase().disconnectDataBase();
    }

    public static void main(String[] args) {
        NotesDatabase.getDatabase();
        launch(args);
    }
}