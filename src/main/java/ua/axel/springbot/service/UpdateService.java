package ua.axel.springbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.springbot.util.Names;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private QuizService quizService;

	@Autowired
	private KeyboardService keyboardService;

	public List<BotApiMethod<Message>> getMethods(Update update) {
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		if (update.hasMessage()) {
			var message = update.getMessage();
			if (message.getText().equals(Names.START_COMMAND)) {
				var inlineKeyboardMarkup = keyboardService.getInlineKeyboardMarkup(
						Names.NEXTQUIZ_BUTTON_CALLBACK_DATA);
				var sendMessage = SendMessage.builder()
						.chatId(message.getChatId().toString())
						.text(Names.WHATSNEXT_TEXT)
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
			if (callbackQueryData.startsWith(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX)) {
				var quizId = callbackQueryData.substring(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX.length());
				var quiz = quizService.findById(quizId);
				botApiMethods.add(SendMessage.builder()
						.chatId(chatId)
						.text(quizService.getQuizInfo(quiz))
						.build());
			} else if (callbackQueryData.equals(Names.NEXTQUIZ_BUTTON_CALLBACK_DATA)) {
				var optionalMessage = messageService.getMessage(update);
				if (optionalMessage.isPresent()) {
					var myPoll = messageService.getSendPool(chatId).orElseThrow();
					var quiz = myPoll.getQuiz();
					botApiMethods.add(myPoll.getSendPoll());
					logger.info("{} ask a poll from {}: {}",
							message.getFrom().getUserName(), quiz.getTopic(), quiz.getQuestion());
					var inlineKeyboardMarkup = keyboardService.getInlineKeyboardMarkup(
							Names.NEXTQUIZ_BUTTON_CALLBACK_DATA,
							Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX + quiz.getId());
					var sendMessage = SendMessage.builder()
							.chatId(chatId)
							.text(Names.WHATSNEXT_TEXT)
							.replyMarkup(inlineKeyboardMarkup)
							.build();
					botApiMethods.add(sendMessage);
				}
			}
		}
		return botApiMethods;
	}
}
