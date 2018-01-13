package com.maxb.cominsight.models;

import lombok.Data;

@Data
public class SuccessResult {
    private boolean success = true;
    private String message;

    public SuccessResult(String message){
        this.message = message;
    }
}
