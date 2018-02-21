package com.maxb.cominsight.models.essential;

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
@Document(collection = "blog")
@EqualsAndHashCode(of = "id")
public class Blog {

    @Id
    private String id;

    @NotNull
    private User member = null;

    @NotNull
    private Company company = null;


    private LocalDateTime created = LocalDateTime.now();

    private String title;
    private String text;
    private String[] tags;

    private int commentsCount = 0;



}
