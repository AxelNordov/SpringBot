package ua.axel.springbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.axel.springbot.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UpdateService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);
	private static final String QUIZINFO_PREFIX = "/quizinfo_";
	private static final String NEXTQUIZ = "/nextquiz";

	@Autowired
	private MessageService messageService;

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuizService quizService;

	public List<BotApiMethod<Message>> getMethods(Update update) {
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		if (update.hasMessage()) {
			var message = update.getMessage();
			if (message.getText().equals("/start")) {
				var buttons = List.of(
						List.of(
								InlineKeyboardButton.builder()
										.text("Next Quiz")
										.callbackData(NEXTQUIZ)
										.build()
						)
				);
				var inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboard(buttons).build();
				var sendMessage = SendMessage.builder()
						.chatId(message.getChatId().toString())
						.text("What's next?")
						.replyMarkup(inlineKeyboardMarkup)
						.build();
				botApiMethods.add(sendMessage);
			}
		}
		if (update.hasCallbackQuery()) {
			var callbackQuery = update.getCallbackQuery();
			var callbackQueryData = callbackQuery.getData();
			var message = callbackQuery.getMessage();
			var chatId = message.getChatId().toString();
			if (callbackQueryData.startsWith(QUIZINFO_PREFIX)) {
				var quizId = callbackQueryData.substring(QUIZINFO_PREFIX.length());
				var quiz = quizRepository.findById(Long.parseLong(quizId)).orElseThrow();
				botApiMethods.add(SendMessage.builder()
						.chatId(chatId)
						.text(quizService.getString(quiz))
						.build());

			} else if (callbackQueryData.equals(NEXTQUIZ)) {
				var optionalMessage = getMessage(update);
				if (optionalMessage.isPresent()) {

					var myPoll = messageService.getSendPool(chatId).orElseThrow();
					var quiz = myPoll.getQuiz();
					botApiMethods.add(myPoll.getSendPoll());
					logger.info("{} ask a poll from {}: {}",
							message.getFrom().getUserName(), quiz.getTopic(), quiz.getQuestion());

					var buttons = List.of(
							List.of(
									InlineKeyboardButton.builder()
											.text("Next Quiz")
											.callbackData(NEXTQUIZ)
											.build(),
									InlineKeyboardButton.builder()
											.text("Quiz info")
											.callbackData(QUIZINFO_PREFIX + quiz.getId())
											.build()
							)
					);
					var inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboard(buttons).build();
					var sendMessage = SendMessage.builder()
							.chatId(chatId)
							.text("What's next?")
							.replyMarkup(inlineKeyboardMarkup)
							.build();
					botApiMethods.add(sendMessage);
				}
			}
		}
		return botApiMethods;
	}

	private Optional<Message> getMessage(Update update) {
		return Optional.ofNullable(update.getMessage())
				.or(() -> Optional.ofNullable(update.getCallbackQuery()).map(CallbackQuery::getMessage));
	}
}
