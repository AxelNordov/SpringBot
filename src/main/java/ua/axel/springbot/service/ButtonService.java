package ua.axel.springbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.axel.springbot.util.Names;

import java.util.HashMap;
import java.util.Map;

@Service
public class ButtonService {

	private static final Map<String, String> buttons = new HashMap<>();

	static {
		buttons.put(Names.NEXTQUIZ_BUTTON_CALLBACK_DATA, Names.NEXTQUIZ_BUTTON_TEXT);
		buttons.put(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX, Names.QUIZINFO_BUTTON_TEXT);
	}

	private InlineKeyboardButton getNewButton(String buttonTest, String buttonCallbackData) {
		return InlineKeyboardButton.builder()
				.text(buttonTest)
				.callbackData(buttonCallbackData)
				.build();
	}

	public InlineKeyboardButton getNewButton(String buttonCallbackData) {
		if (buttonCallbackData.startsWith(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX)) {
			return getNewButton(buttons.get(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX), buttonCallbackData);
		} else {
			return getNewButton(buttons.get(buttonCallbackData), buttonCallbackData);
		}
	}
}
