package com.mordor.speach.dao.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sub_category")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubCategoryDoc {
    @Id
    private String id;

    @DBRef
    private List<QuestionDoc> questions;
}
