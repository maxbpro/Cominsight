package com.maxb.cominsight.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {

    private String userId = null;
    private String userName = null;
    private String photoId = null;
    private LocalDateTime created = null;
}
