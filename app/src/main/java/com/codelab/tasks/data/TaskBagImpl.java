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

    /**
     * Construtor que inicia o serviço do Google
     */
    public TaskBagImpl() {
        mTaks = new ArrayList<>();

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)

                //TODO - inserir o endereço correto do serviço
                .setRootUrl("https://project_id.appspot.com/_ah/api/")
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


    /**
     * metodo para gravar tarefas no serviço Google
     */
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

            //TODO - armarzenar task no serviço google - invocar o store task do objeto taskApiService
        }
    }



    /**
     * metodo para recuperar tarefas do serviço Google
     */
    @Override
    public synchronized void pullFromRemote() throws IOException {
        List<TaskBean> remoteTasks = null;

        //TODO - listar todas as tarefas armazenadas no serviço do google - listar tasks pelo taskApiService

        //Converte os objetos task do serviço nos objetos task local
        if(remoteTasks != null) {
            getTasks().clear();

            for (TaskBean taskBean : remoteTasks) {
                mTaks.add(new Task(taskBean.getId(), taskBean.getText(), taskBean.getCompleted(), taskBean.getPrependedDate()));
            }
        }
    }
}
