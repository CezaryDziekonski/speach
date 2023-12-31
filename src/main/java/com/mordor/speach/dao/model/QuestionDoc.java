package com.mordor.speach.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "questions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class QuestionDoc {
    @Id
    private String id;

    private String content;

    private List<AnswerDoc> answers;
}
