package com.example.petclinic.model;

import java.io.Serializable;


public abstract class BaseEntity implements Serializable {

    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
