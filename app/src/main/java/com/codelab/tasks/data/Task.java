package com.codelab.tasks.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thales on 2/23/15.
 */
public class Task {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private Long id;
    private  String text;
    private boolean completed = false;
    private String date;

    public Task() {
    }

    public Task(String text, String date) {
        this.date = date;
        this.text = text;
    }

    public Task(String text, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        this.date = formatter.format(date);
        this.text = text;
    }

    public Task(long id, String text, boolean completed, String date) {
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
