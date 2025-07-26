package com.learn.knowly.service;

import com.learn.knowly.dao.QuestionDao;
import com.learn.knowly.dao.QuizDao;
import com.learn.knowly.model.Question;
import com.learn.knowly.model.QuestionWrapper;
import com.learn.knowly.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        questionsFromDb.forEach(question -> {
            QuestionWrapper q = new QuestionWrapper(question.getId(), question.getQuestionTitle(),question.getOption1(), question.getOption2(),question.getOption3(),question.getOption4());
            questionsForUser.add(q);
        });
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }
}
