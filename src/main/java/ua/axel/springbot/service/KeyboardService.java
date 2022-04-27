package ua.axel.springbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.axel.springbot.util.Names;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KeyboardService {

	private static final Map<String, String> buttons = new HashMap<>();

	static {
		buttons.put(Names.NEXTQUIZ_BUTTON_CALLBACK_DATA, Names.NEXTQUIZ_BUTTON_TEXT);
		buttons.put(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX, Names.QUIZINFO_BUTTON_TEXT);
	}

	private InlineKeyboardMarkup getInlineKeyboardMarkup(List<InlineKeyboardButton> inlineKeyboardButtonList) {
		var buttons = List.of(inlineKeyboardButtonList);
		return InlineKeyboardMarkup.builder().keyboard(buttons).build();
	}

	public InlineKeyboardMarkup getInlineKeyboardMarkup(String... buttonCallbackDatas) {
		return getInlineKeyboardMarkup(
				Arrays.stream(buttonCallbackDatas)
						.map(this::getNewButton)
						.collect(Collectors.toList()));
	}

	private InlineKeyboardButton getNewButton(String buttonTest, String buttonCallbackData) {
		return InlineKeyboardButton.builder()
				.text(buttonTest)
				.callbackData(buttonCallbackData)
				.build();
	}

	public InlineKeyboardButton getNewButton(String buttonCallbackDataPrefix) {
		if (buttonCallbackDataPrefix.startsWith(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX)) {
			return getNewButton(buttons.get(Names.QUIZINFO_BUTTON_CALLBACK_DATA_PREFIX), buttonCallbackDataPrefix);
		} else {
			return getNewButton(buttons.get(buttonCallbackDataPrefix), buttonCallbackDataPrefix);
		}
	}
}
