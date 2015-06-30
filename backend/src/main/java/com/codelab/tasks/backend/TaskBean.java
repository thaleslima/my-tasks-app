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
    private String prependedDate;

    public TaskBean() {
    }

    public TaskBean(String text, String prependedDate) {
        this.prependedDate = prependedDate;
        this.text = text;
    }

    public TaskBean(Long id, String text, boolean completed, String prependedDate) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.prependedDate = prependedDate;
    }

    //TODO implementar todos os gets e sets das propriedades da classe
}