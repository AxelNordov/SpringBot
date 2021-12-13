package ua.axel.springbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import ua.axel.springbot.MyPoll;
import ua.axel.springbot.entity.Answer;
import ua.axel.springbot.entity.Quiz;
import ua.axel.springbot.repository.QuizRepository;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

	private static final String QUIZ_TYPE = "quiz";

	@Autowired
	private QuizRepository quizRepository;

	public Optional<MyPoll> getSendPool(String chatId) {
		var quiz = quizRepository.findRandomQuizWithRightAnswer();
		var sendPoll = getSendPollFromQuiz(chatId, quiz);
		return Optional.of(new MyPoll(sendPoll, quiz));
	}

	private SendPoll getSendPollFromQuiz(String chatId, Quiz quiz) {
		var options = quiz.getAnswers().stream()
				.sorted(Comparator.comparingInt(Answer::getOrderNumber))
				.map(Answer::getText)
				.collect(Collectors.toList());
		return SendPoll.builder()
				.chatId(chatId)
				.options(options)
				.explanation(quiz.getLink1())
				.question(quiz.getQuestion())
				.type(QUIZ_TYPE)
				.correctOptionId(getCorrectOptionId(quiz.getRightAnswer()))
				.build();
	}

	private int getCorrectOptionId(Byte rightAnswer) {
		return rightAnswer - 1;
	}

}
