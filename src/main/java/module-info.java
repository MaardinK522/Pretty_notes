module com.mkproductions.notesapplication {
    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.slf4j;

    exports com.mkproductions.notesapplication;
    exports com.mkproductions.notesapplication.adapters;
    exports com.mkproductions.notesapplication.pojo;
    exports com.mkproductions.notesapplication.databases;
    exports com.mkproductions.notesapplication.controllers;
}