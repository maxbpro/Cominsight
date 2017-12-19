package com.maxb.cominsight.models.essential;

import com.maxb.cominsight.models.Comment;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "photo")
public class Photo {

    @Id
    private String id;

    private String photoUrl = null;
    private LocalDateTime created = LocalDateTime.now();

    private User user = null;
    private Company company = null;

    private List<Comment> comments = new ArrayList();
}
