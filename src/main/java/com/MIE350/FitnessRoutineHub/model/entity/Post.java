package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String body;

    private String comments;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

    public void setComments(String comments) throws Exception {
        JSONParser parser = new JSONParser(comments);
        Object result = parser.parse();
        if (!(result instanceof List)) {
            throw new Exception("Comments object should be an json array");
        }
        this.comments = comments;
    }

}
