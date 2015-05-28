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

    //Todo Add campos: "text" (tarefa), "completed" (indica se a tarefa foi realizada) e "date" (data da tarefa)
}