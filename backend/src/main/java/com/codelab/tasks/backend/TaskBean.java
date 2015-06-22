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

    //TODO implementar todos os gets e sets das propriedades da classe
}