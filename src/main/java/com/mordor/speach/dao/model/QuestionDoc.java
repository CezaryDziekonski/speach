package com.mordor.speach.dao.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions")
public class QuestionDoc {
    @Id
    private String id;

    private String content;




}
