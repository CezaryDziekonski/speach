package com.mordor.speach.api;

import com.mordor.speach.dao.model.QuestionDoc;
import com.mordor.speach.manager.QuestionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionManager questionManager;

    @PostMapping("/add")
    public QuestionDoc addQuestion(@RequestBody QuestionDoc questionDoc) {
        return questionManager.addQuestion(questionDoc);
    }

    @GetMapping("/{id}")
    public QuestionDoc getQuestion(@PathVariable String id) {
        return questionManager.getQuestion(id);
    }

}
