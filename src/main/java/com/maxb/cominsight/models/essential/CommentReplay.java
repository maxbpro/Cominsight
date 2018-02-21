package com.maxb.cominsight.models.essential;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "comment_replay")
@EqualsAndHashCode(of = "id")
public class CommentReplay {

    @Id
    private String id;

    @DBRef
    private Comment comment;

    @DBRef
    private User member = null;

    private String text = null;

    private LocalDateTime created = LocalDateTime.now();

}
