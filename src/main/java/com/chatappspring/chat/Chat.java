package com.chatappspring.chat;

import com.chatappspring.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Chat {

    @Id @GeneratedValue Long id;
    @ManyToOne
    User sender;
    String message;

    Timestamp createdAt;

    @PrePersist // method will be called before save happens
    public void beforeSave(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
        //you can change any other fields what you would like to modify here
    }
}
