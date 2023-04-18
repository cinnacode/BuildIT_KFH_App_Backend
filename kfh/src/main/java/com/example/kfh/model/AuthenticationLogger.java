package com.example.kfh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Document("login_logs")
public class AuthenticationLogger {
    public ObjectId id;

    public String date;

    public String headers;

    public String username;
}

