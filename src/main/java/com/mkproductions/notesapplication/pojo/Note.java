package com.mkproductions.notesapplication.pojo;


import javafx.scene.paint.Color;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Note {
    String id;
    String noteTopic;
    String noteDescription;
    Color noteColor;

    public Note() {
        LocalDateTime date = java.time.LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(date);
        System.out.println("Note time: " + timestamp);
        this.id = timestamp.toString();
        this.noteTopic = "";
        this.noteDescription = "";
        this.noteColor = Color.TRANSPARENT;
    }

    public Note(String noteTopic, String noteDescription, Color noteColor) {
        LocalDateTime date = java.time.LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(date);
        System.out.println("Note time: " + timestamp);
        this.id = timestamp.toString();
        this.noteTopic = noteTopic;
        this.noteDescription = noteDescription;
        this.noteColor = noteColor;
    }

    public void setNoteTopic(String noteTopic) {
        this.noteTopic = noteTopic;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setNoteColor(Color noteColor) {
        this.noteColor = noteColor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteTopic() {
        return this.noteTopic;
    }

    public String getNoteDescription() {
        return this.noteDescription;
    }

    public Color getNoteColor() {
        return this.noteColor;
    }

    public String getId() {
        return this.id;
    }
}
