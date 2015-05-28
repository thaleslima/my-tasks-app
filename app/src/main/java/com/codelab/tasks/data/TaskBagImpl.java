package com.codelab.tasks.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thales on 2/23/15.
 */
public class TaskBagImpl implements TaskBag {
    private List<Task> mTaks;

    public TaskBagImpl() {
    }

    @Override
    public void addAsTask(String input) {
        mTaks.add(new Task(input, new Date()));
    }

    @Override
    public List<Task> getTasks() {
        if(mTaks == null)
            mTaks = new ArrayList<>();

        return mTaks;
    }

    @Override
    public void removeSelectedItems(List<Integer> positions) {
        List<Task> items = new ArrayList<>(positions.size());
        for (int i = 0; i < positions.size(); i++) {
            items.add(getTasks().get(positions.get(i)));
        }

        getTasks().removeAll(items);
    }

    @Override
    public void alterCompleteItems(List<Integer> positions) {
        Task task;

        for (int i = 0; i < positions.size(); i++) {
            task = getTasks().get(positions.get(i));
            task.setCompleted(!task.isCompleted());
        }
    }

    @Override
    public synchronized void pushToRemote() throws IOException {
        //Todo - deletar todas as tarefas

        //Todo - armazenar todas as tarefas atuais
    }

    @Override
    public synchronized void pullFromRemote() throws IOException {
        //Todo - recuperar tarefas
    }
}
