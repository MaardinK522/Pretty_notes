package com.mkproductions.notesapplication.pojo;

import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.internal.CursorFactory;

// THIS CODE IS GENERATED BY ObjectBox, DO NOT EDIT.

/**
 * ObjectBox generated Cursor implementation for "Note".
 * Note that this is a low-level class: usually you should stick to the Box class.
 */
public final class NoteCursor extends Cursor<Note> {
    @Internal
    static final class Factory implements CursorFactory<Note> {
        @Override
        public Cursor<Note> createCursor(io.objectbox.Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new NoteCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    private static final Note_.NoteIdGetter ID_GETTER = Note_.__ID_GETTER;


    private final static int __ID_noteTopic = Note_.noteTopic.id;
    private final static int __ID_noteDescription = Note_.noteDescription.id;

    public NoteCursor(io.objectbox.Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Note_.__INSTANCE, boxStore);
    }

    @Override
    public long getId(Note entity) {
        return ID_GETTER.getId(entity);
    }

    /**
     * Puts an object into its box.
     *
     * @return The ID of the object within its box.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public long put(Note entity) {
        String noteTopic = entity.noteTopic;
        int __id1 = noteTopic != null ? __ID_noteTopic : 0;
        String noteDescription = entity.noteDescription;
        int __id2 = noteDescription != null ? __ID_noteDescription : 0;

        long __assignedId = collect313311(cursor, entity.id, PUT_FLAG_FIRST | PUT_FLAG_COMPLETE,
                __id1, noteTopic, __id2, noteDescription,
                0, null, 0, null,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);

        entity.id = __assignedId;

        return __assignedId;
    }

}