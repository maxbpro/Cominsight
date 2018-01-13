package com.maxb.cominsight.config.response;

import com.maxb.cominsight.config.exceptions.ApiError;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ResponseEnvelope {


    private Object result;

    private List<ApiError> errors;


    public ResponseEnvelope addError(ApiError error) {
        if (errors == null) {
            errors = new ArrayList<ApiError>();
        }
        errors.add(error);
        return this;
    }

    public ResponseEnvelope addResult(Object result) {
        if (this.result == null) {
            this.result = result;
        }
        return this;
    }


    public Object getResult() {
        return result;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

}
