package com.mkproductions.notesapplication.databases;

import com.mkproductions.notesapplication.pojo.Note;
import javafx.scene.paint.Color;


import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesDatabase {
    private static NotesDatabase database;
    private static ExecutorService threadPool;

    private static Connection connection = null;
    private static Statement statement;
    private static final String notesTable = "notes_table";

    private NotesDatabase() {
        // Empty for singleton design patterns.
    }

    public static NotesDatabase getDatabase() {
        if (database == null) {
            String dir = System.getProperty("user.dir");
            System.out.println("User dir: " + dir);
            String sqlLiteJarFilePath = dir + "\\src\\main\\resources\\com\\mkproductions\\notesapplication\\database\\notes_database.db";
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + sqlLiteJarFilePath);
                statement = connection.createStatement();
                statement.execute("CREATE TABLE IF NOT EXISTS " + notesTable + " ( id text PRIMAY KEY, " + "note_topic text NOT NULL, " + "note_description text, " + "color_r double, " + "color_g double, " + "color_b double );");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println();
            System.out.println("NotesApplication has created a connection with database.");
            System.out.println();
            threadPool = Executors.newCachedThreadPool();
            database = new NotesDatabase();
        }
        return database;
    }

    public void insertNote(Note... notes) {
        threadPool.execute(() -> {
            for (var note : notes) {
                if (note != null) try {
                    statement = connection.createStatement();
                    String sql = "INSERT INTO " + notesTable + " (id, note_topic, note_description, color_r, color_g, color_b) " + " VALUES ('" + note.getId() + "','" + note.getNoteTopic() + "','" + note.getNoteDescription() + "'," + note.getNoteColor().getRed() + "," + note.getNoteColor().getGreen() + "," + note.getNoteColor().getBlue() + " );";
                    statement.execute(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void updateNote(Note note) {
        threadPool.execute(() -> {
            try {
                statement = connection.createStatement();
                String updateNote = "UPDATE " + notesTable + "  SET note_topic = '" + note.getNoteTopic() + "', " + "note_description = '" + note.getNoteDescription() + "', " + "color_r = " + note.getNoteColor().getRed() + ", " + "color_g = " + note.getNoteColor().getGreen() + ", " + "color_b = " + note.getNoteColor().getBlue() + " WHERE id = '" + note.getId() + "';";
                statement.execute(updateNote);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void removeNote(String noteID) {
        threadPool.execute(() -> {
            try {
                statement = connection.createStatement();
                statement.execute("DELETE FROM " + notesTable + " where id = '" + noteID + "';");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<ArrayList<Note>> getAllNotes() {
        return CompletableFuture.supplyAsync(() -> {
            ArrayList<Note> allNotes = new ArrayList<>();
            try {
                statement.execute("SELECT * from " + notesTable + ";");
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setId(resultSet.getString("id"));
                    note.setNoteTopic(resultSet.getString("note_topic"));
                    note.setNoteDescription(resultSet.getString("note_description"));
                    note.setNoteColor(Color.color(resultSet.getDouble("color_r"), resultSet.getDouble("color_g"), resultSet.getDouble("color_b")));
                    allNotes.add(note);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return allNotes;
        });
    }

    public void disconnectDataBase() {
        try {
            statement.close();
            connection.close();
            threadPool.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
