package com.mkproductions.notesapplication.controllers;

import com.mkproductions.notesapplication.adapters.NoteViewCellAdapter;
import com.mkproductions.notesapplication.databases.NotesDatabase;
import com.mkproductions.notesapplication.pojo.Note;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    public TextField mNoteTopicTextField;
    @FXML
    public TextArea mNoteDescriptionTextArea;
    @FXML
    public Button mAddNoteButton;
    @FXML
    public ListView<Note> mNotesListView;
    @FXML
    public ColorPicker mNoteColorColorPicker;
    @FXML
    public BorderPane mNotePanel;

    public static final ObservableList<Note> mNotesList = FXCollections.observableArrayList();

    private NotesDatabase notesDatabase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notesDatabase = NotesDatabase.getDatabase();
        notesDatabase.getAllNotes().thenAccept(mNotesList::setAll);
        mNotesListView.setItems(mNotesList);
        mNotesListView.setCellFactory(noteListView -> new NoteViewCellAdapter());
        mNotesListView.setOnMouseClicked(mouseEvent -> {
//            if (mouseEvent.getClickCount() == 1) {
//                int selectedIndex = mNotesListView.getSelectionModel().getSelectedIndex();
//                System.out.println("Selected Item: " + selectedIndex);
//            }
            if (mouseEvent.getClickCount() == 2) {
                Note note = mNotesListView.getSelectionModel().getSelectedItem();
                mNoteDescriptionTextArea.setText(note.getNoteDescription());
                mNoteTopicTextField.setText(note.getNoteTopic());
                mNotePanel.setBackground(new Background(new BackgroundFill(note.getNoteColor(), CornerRadii.EMPTY, Insets.EMPTY)));
                mAddNoteButton.setText("EDIT");
            }
        });
        mNoteTopicTextField.textProperty().addListener(event -> mNoteTopicTextField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), !mNoteTopicTextField.getText().isEmpty()));
        mNoteDescriptionTextArea.textProperty().addListener(event -> mNoteDescriptionTextArea.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), !mNoteDescriptionTextArea.getText().isEmpty()));
        mNoteColorColorPicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                mNotePanel.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }));
    }

    @FXML
    public void onRefreshPressed() {
        notesDatabase.getAllNotes().thenAccept(mNotesList::setAll);
    }

    @FXML
    public void onAddNoteButton() {
        if (mAddNoteButton.getText().equals("ADD")) {
            if (!mNoteTopicTextField.getText().isEmpty()) {
                Note note = new Note();
                note.setNoteTopic(mNoteTopicTextField.getText());
                note.setNoteDescription(mNoteDescriptionTextArea.getText());
                note.setNoteColor(mNoteColorColorPicker.getValue());
                notesDatabase.insertNote(note);
                mNotesList.add(note);
                mNoteTopicTextField.clear();
                mNoteDescriptionTextArea.clear();
            }
        } else {
            if (!mNoteTopicTextField.getText().isEmpty()) {
                Note note = mNotesListView.getSelectionModel().getSelectedItem();
                note.setNoteTopic(mNoteTopicTextField.getText());
                note.setNoteDescription(mNoteDescriptionTextArea.getText());
                note.setNoteColor(mNoteColorColorPicker.getValue());
                notesDatabase.updateNote(note);
                mNoteTopicTextField.clear();
                mNoteDescriptionTextArea.clear();
                mAddNoteButton.setText("ADD");
            }
        }
    }
}