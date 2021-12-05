package ua.axel.springbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.axel.springbot.service.MessageService;

@Component
public class SpringBot extends TelegramLongPollingBot {

	private final MessageService messageService;

	@Value("${telegram.bot.username}")
	private String botUsername;

	@Value("${telegram.bot.token}")
	private String botToken;

	@Autowired
	public SpringBot(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public void onUpdateReceived(Update update) {
		messageService.getMethod(update).ifPresent(this::executeMethod);
	}

	private Message executeMethod(BotApiMethod<Message> method) {
		Message execute = new Message();
		try {
			execute = execute(method);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		return execute;
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

}
