/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.codelab.tasks.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import javax.inject.Named;

import static com.codelab.tasks.backend.OfyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.tasks.codelab.com", ownerName = "backend.tasks.codelab.com", packagePath = ""))
public class MyEndpoint {

    //Exemplo de metodo
    @ApiMethod(name = "sayHi", path = "tasks", httpMethod = ApiMethod.HttpMethod.GET)
    public TaskBean sayHi(@Named("name") String task) {
        TaskBean response = new TaskBean();

        return response;
    }


    //TODO - implementar metodo para listar de uma tarefa
    //Name: storeTask
    //path: task
    //httpMethod: POST
    //retorno: void
    //parameto: Objeto de task
    //Gravar dados usando o objeto de 'OFY' Ex.: ofy().save.entity(entidade).now();


    //TODO - implementar metodo para listar todas as tarefas
    //Name: getTasks
    //path: task
    //httpMethod: GET
    //retorno: List de tasks
    //Listar dados usando o objeto de 'OFY' Ex.: ofy().load().type(entidade.class).list();


    //TODO - implementar metodo para deletar todas as tarefas
    //Name: clearTasks
    //path: task
    //httpMethod: DELETE
    //retorno: void
    //Listar dados usando o objeto de 'OFY' Ex.: ofy().delete().entities(lista de entidades).now();;
}
