package com.example.CodeFellowship;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String body;
    String createdAt;
    @ManyToOne
    ApplicationUser owner;

    public ApplicationUser getOwner() {
        return owner;
    }

    public Post(){}

    public Integer getId() {
        return id;
    }

    public Post(String body, ApplicationUser owner){
        this.body = body;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        this.createdAt = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
        this.owner = owner;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }


}
