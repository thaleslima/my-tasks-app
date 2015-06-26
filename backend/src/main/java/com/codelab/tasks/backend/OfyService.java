package com.codelab.tasks.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by thales on 5/28/15.
 */
public class OfyService {
    static {

        //TODO - Registrar entidade TaskBean no Objectfy. Ex.: ObjectifyService.register(Entity.class);

    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
