package com.maxb.cominsight.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Comment {

    @Id
    private String id;
    private LocalDateTime created = LocalDateTime.now();
    private String text = null;
    private String user_id = null;
    private String photo_id = null;
}
