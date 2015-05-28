package com.codelab.tasks.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class TaskBean {

    @Id
    private Long id;
    private String text;
    private boolean completed = false;
    private String date;

    public TaskBean() {
    }

    public TaskBean(String text, String date) {
        this.date = date;
        this.text = text;
    }

    public TaskBean(Long id, String text, boolean completed, String date) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPrependedDate() {
        return date;
    }

    public void setPrependedDate(String prependedDate) {
        this.date = prependedDate;
    }
}