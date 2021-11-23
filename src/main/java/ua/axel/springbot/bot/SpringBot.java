package ua.axel.springbot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SpringBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		//
	}

	@Override
	public String getBotUsername() {
		return null;
	}

	@Override
	public String getBotToken() {
		return null;
	}

}
