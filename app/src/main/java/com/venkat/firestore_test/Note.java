package com.venkat.firestore_test;

import com.google.firebase.firestore.Exclude;

public class Note {

    private String title;
    private String description;
    private String documentId;

    public Note() {
        //public no-arg constructor needed
        //firestore always needs an empty constructor
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
