package com.maxb.cominsight.models.essential;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "comment")
@EqualsAndHashCode(of = "id")
public class Comment {

    @Id
    private String id;

    @DBRef
    private Post post;

    @DBRef
    private Blog blog;

    @DBRef
    private User member = null;

    private String text = null;

    private LocalDateTime created = LocalDateTime.now();

}
