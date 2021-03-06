package com.codelab.tasks.data;

import com.codelab.tasks.backend.myApi.MyApi;
import com.codelab.tasks.backend.myApi.model.TaskBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by thales on 2/23/15.
 */
public class TaskBagImpl implements TaskBag {
    private List<Task> mTaks;
    private MyApi taskApiService;

    public TaskBagImpl() {
        mTaks = new ArrayList<>();

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("https://task-gdg-maven.appspot.com/_ah/api/")
                .setGoogleClientRequestInitializer( new GoogleClientRequestInitializer() {
                                                        @Override
                                                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                                                throws IOException {
                                                            //abstractGoogleClientRequest.setDisableGZipContent(true);
                                                        }
                                                    }

                );
        taskApiService = builder.build();
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
        taskApiService.clearTasks().execute();

        Long id = 0L;

        for (Task task : getTasks()) {
            TaskBean taskBean = new TaskBean();
            taskBean.setId(++id);
            taskBean.setText(task.getText());
            taskBean.setCompleted(task.isCompleted());
            taskBean.setPrependedDate(task.getPrependedDate());
            taskApiService.storeTask(taskBean).execute();
        }
    }

    @Override
    public synchronized void pullFromRemote() throws IOException {
        List<TaskBean> remoteTasks = taskApiService.getTasks().execute().getItems();

        getTasks().clear();

        if(remoteTasks != null) {
            for (TaskBean taskBean : remoteTasks) {
                mTaks.add(new Task(taskBean.getId(), taskBean.getText(), taskBean.getCompleted(), taskBean.getPrependedDate()));
            }

            Comparator<Task> comparator = new Comparator<Task>() {
                public int compare(Task c1, Task c2) {
                    return c1.getId().compareTo(c2.getId());
                }
            };

            Collections.sort(mTaks, comparator);
        }
    }
}
