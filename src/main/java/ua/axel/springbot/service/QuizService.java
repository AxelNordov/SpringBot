package ua.axel.springbot.service;

import org.springframework.stereotype.Service;
import ua.axel.springbot.entity.Quiz;

@Service
public class QuizService {

	public String getString(Quiz quiz) {
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
