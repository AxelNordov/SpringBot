package ua.axel.springbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.springbot.entity.Answer;
import ua.axel.springbot.entity.Quiz;
import ua.axel.springbot.repository.QuizRepository;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
	private final QuizRepository quizRepository;

	@Autowired
	public MessageService(QuizRepository quizRepository) {
		this.quizRepository = quizRepository;
	}

	public Optional<BotApiMethod<Message>> getMethod(Update update) {
		return Optional.ofNullable(getNextRandomQuizWithAnswer(update));
	}

	public SendPoll getNextRandomQuizWithAnswer(Update update) {
		var quiz = quizRepository.findRandomQuizWithRightAnswer();
		var sendPoll = getSendPollFromQuiz(getChatId(update), quiz);
		logger.info("{} ask a poll from {}: {}",
				update.getMessage().getFrom().getUserName(), quiz.getTopic(), quiz.getQuestion());
		return sendPoll;
	}

	private SendPoll getSendPollFromQuiz(String chatId, Quiz quiz) {
		var options = quiz.getAnswers().stream()
				.sorted(Comparator.comparingInt(Answer::getOrderNumber))
				.map(Answer::getText)
				.collect(Collectors.toList());
		String question;
		String explanation = quiz.getLink1();
		String type = null;
		Integer correctOptionId = null;
		if (quiz.getRightAnswer() != null && quiz.getRightAnswer() >= 0) {
			question = quiz.getQuestion();
			type = "quiz";
			correctOptionId = quiz.getRightAnswer() - 1;
		} else {
			question = quiz.getQuestion() + " (no answer)";
		}
		return SendPoll.builder()
				.chatId(chatId)
				.options(options)
				.explanation(explanation)
				.question(question)
				.type(type)
				.correctOptionId(correctOptionId)
				.build();
	}

	private String getChatId(Update update) {
		return update.getMessage().getChatId().toString();
	}

}
