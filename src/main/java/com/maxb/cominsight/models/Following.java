package com.maxb.cominsight.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Following {

    @NotNull
    private String companyId = null;

    @NotNull
    private String companyTitle = null;
    private String companyPhotoUrl = null;
}
