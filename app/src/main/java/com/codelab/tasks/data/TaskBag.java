package com.codelab.tasks.data;
import java.io.IOException;
import java.util.List;

/**
 * Created by thales on 2/23/15.
 */
public interface TaskBag {
    void addAsTask(String input);

    List<Task> getTasks();

    void removeSelectedItems(List<Integer> positions);

    void alterCompleteItems(List<Integer> positions);

    void pushToRemote() throws IOException;

    void pullFromRemote() throws IOException;
}
