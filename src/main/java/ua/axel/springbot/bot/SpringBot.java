package ua.axel.springbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.axel.springbot.service.UpdateService;

@Component
public class SpringBot extends TelegramLongPollingBot {

	@Autowired
	private UpdateService updateService;

	@Value("${telegram.bot.username}")
	private String botUsername;

	@Value("${telegram.bot.token}")
	private String botToken;

	@Override
	public void onUpdateReceived(Update update) {
		updateService.getMethods(update).forEach(this::executeMethod);
	}

	private void executeMethod(BotApiMethod<Message> method) {
		try {
			execute(method);
		} catch (TelegramApiException e) {
			e.printStackTrace();
			throw new RuntimeException("TODO", e);
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
