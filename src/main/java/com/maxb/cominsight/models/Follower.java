package com.maxb.cominsight.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Follower {

    private String user_id = null;
    private LocalDateTime created = null;

}