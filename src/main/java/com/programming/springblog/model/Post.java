package com.programming.springblog.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Lob
    @Column
    @NotBlank
    private String content;

    @Column
    private Instant CreatedOn;

    @Column
    private Instant UpdatedOn;

    @Column
    @NotBlank
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Instant createdOn) {
        CreatedOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        UpdatedOn = updatedOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }


}
