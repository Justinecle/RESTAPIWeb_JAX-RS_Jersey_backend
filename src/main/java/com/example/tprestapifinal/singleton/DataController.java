package com.example.tprestapifinal.singleton;

import jakarta.persistence.*;

public class DataController {
    private static DataController singleInstance = null;
    public EntityManager manager = null;
    private DataController(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        this.manager = factory.createEntityManager();
    }

    public static DataController getSingleInstance() {
        if (singleInstance == null) {
            singleInstance = new DataController();
        }
        return singleInstance;
    }
}