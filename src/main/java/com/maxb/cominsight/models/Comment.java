package com.maxb.cominsight.models;

import com.maxb.cominsight.models.essential.Photo;
import com.maxb.cominsight.models.essential.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "comment")
@EqualsAndHashCode(of = "id")
public class Comment {

    @Id
    private String id;

    private LocalDateTime created = LocalDateTime.now();
    private String text = null;
    private User user = null;
    private Photo photo = null;
}
