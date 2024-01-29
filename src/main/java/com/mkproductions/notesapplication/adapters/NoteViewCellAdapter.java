package com.mkproductions.notesapplication.adapters;

import com.mkproductions.notesapplication.NotesApplication;
import com.mkproductions.notesapplication.databases.NotesDatabase;
import com.mkproductions.notesapplication.pojo.Note;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class NoteViewCellAdapter extends ListCell<Note> {
    @FXML
    public Label mNoteTopic;
    @FXML
    public Button mNoteDeleteButton;
    @FXML
    public HBox mNoteHBox;

    public NoteViewCellAdapter() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(NotesApplication.class.getResource("note_item_cell_view.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Note note, boolean empty) {
        super.updateItem(note, empty);
        if (note != null) {
            mNoteTopic.setBackground(new Background(new BackgroundFill(note.getNoteColor(), new CornerRadii(10), Insets.EMPTY)));
            mNoteTopic.setText(note.getNoteTopic());
            mNoteDeleteButton.setOnAction(actionEvent -> NotesDatabase.getDatabase().removeNote(note.getId()));
            mNoteHBox.setBackground(new Background(new BackgroundFill(note.getNoteColor(), new CornerRadii(10), Insets.EMPTY)));
            setBackground(new Background(new BackgroundFill(note.getNoteColor(), new CornerRadii(10), Insets.EMPTY)));
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return;
        }
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
}
