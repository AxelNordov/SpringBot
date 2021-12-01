package ua.axel.springbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
		try {
			execute(messageService.getMethod(update).get());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
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
