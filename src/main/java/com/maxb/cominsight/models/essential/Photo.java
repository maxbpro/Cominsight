package com.maxb.cominsight.models.essential;

import com.maxb.cominsight.models.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "photo")
@EqualsAndHashCode(of = "id")
public class Photo {

    @Id
    private String id;

    @NotNull
    private User user = null;

    @NotNull
    private Company company = null;

    @DBRef
    private List<Comment> comments = new ArrayList();

    private String photoUrl = null;

    private LocalDateTime created = LocalDateTime.now();
}
