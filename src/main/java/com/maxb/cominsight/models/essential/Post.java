package com.maxb.cominsight.models.essential;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "photo")
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    private String id;

    @NotNull
    private User member = null;

    @NotNull
    private Company company = null;

    private String photoUrl = null;

    private LocalDateTime created = LocalDateTime.now();

    private String category = null;
    private String title = null;
    private String text = null;
    private int commentsCount = 0;

    private String[] tags = null;
}
