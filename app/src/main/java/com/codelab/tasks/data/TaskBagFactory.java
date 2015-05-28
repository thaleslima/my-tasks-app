package com.codelab.tasks.data;

/**
 * Created by thales on 2/23/15.
 */
public class TaskBagFactory {

    public static TaskBag getTaskBag()
    {
        return new TaskBagImpl();
    }
}
