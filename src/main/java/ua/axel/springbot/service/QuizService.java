package ua.axel.springbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.axel.springbot.entity.Quiz;
import ua.axel.springbot.repository.QuizRepository;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz findById(long id) {
        return quizRepository.findById(id).orElseThrow();
    }

    public Quiz findById(String id) {
        return findById(Long.parseLong(id));
    }

    public String getQuizInfo(Quiz quiz) {
        return "Quiz" +
                "\nquestion = \"" + quiz.getQuestion() + "\"" +
                "\nsubject = " + (quiz.getSubject() == null ? "" : quiz.getSubject()) +
                "\nsubSubject = " + (quiz.getSubSubject() == null ? "" : quiz.getSubSubject()) +
                "\ntopic = " + (quiz.getTopic() == null ? "" : quiz.getTopic()) +
                "\ndescription = " + (quiz.getDescription() == null ? "" : quiz.getDescription()) +
                "\nlink1 = " + (quiz.getLink1() == null ? "" : quiz.getLink1()) +
                "\nlink2 = " + (quiz.getLink2() == null ? "" : quiz.getLink2()) +
                "\nlinkSource = " + (quiz.getLinkSource() == null ? "" : quiz.getLinkSource());
    }
}
