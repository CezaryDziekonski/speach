package com.mordor.speach.manager;

import com.mordor.speach.dao.model.QuestionDoc;
import com.mordor.speach.dao.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionManager {
    private final QuestionRepository questionRepository;

    public QuestionDoc addQuestion(QuestionDoc questionDoc) {
        return questionRepository.save(questionDoc);
    }

    public QuestionDoc getQuestion(String id) {
        return questionRepository.findById(id).orElseThrow( () -> new RuntimeException("Question not found"));
    }

    public List<QuestionDoc> getQuestions() {
        return questionRepository.findAll();
    }
}
