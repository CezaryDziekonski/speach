package com.mordor.speach.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AnswerDoc {
    private String content;
}
