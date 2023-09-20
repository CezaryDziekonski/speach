package com.mordor.speach.dao.repository;

import com.mordor.speach.dao.model.QuestionDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<QuestionDoc, String> {
}
