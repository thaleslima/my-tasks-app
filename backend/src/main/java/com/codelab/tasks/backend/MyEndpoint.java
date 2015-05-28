/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.codelab.tasks.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;

import static com.codelab.tasks.backend.OfyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.tasks.codelab.com", ownerName = "backend.tasks.codelab.com", packagePath = ""))
public class MyEndpoint {

    //POST https://<project_id>.appspot.com/_ah/api/taskApi/v1/tasks
    @ApiMethod(name = "storeTask", path = "tasks", httpMethod = ApiMethod.HttpMethod.POST)
    public void storeTask(TaskBean taskBean) {
        ofy().save().entity(taskBean).now();
    }


    //GET https://<project_id>.appspot.com/_ah/api/taskApi/v1/tasks
    @ApiMethod(name = "getTasks", path = "tasks", httpMethod = ApiMethod.HttpMethod.GET)
    public List<TaskBean> getTasks() {
        return ofy().load().type(TaskBean.class).list();
    }


    //DELETE https://<project_id>.appspot.com/_ah/api/taskApi/v1/tasks
    @ApiMethod(name = "clearTasks", path = "tasks", httpMethod = ApiMethod.HttpMethod.DELETE)
    public void clearTasks() {
        ofy().delete().entities(ofy().load().type(TaskBean.class).list()).now();
    }
}
