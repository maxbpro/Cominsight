package com.maxb.cominsight.config.exceptions;


public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(Class clazz) {
        super(clazz.getSimpleName());
    }

}
