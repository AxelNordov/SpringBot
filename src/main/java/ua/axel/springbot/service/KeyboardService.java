package ua.axel.springbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyboardService {

	@Autowired
	private ButtonService buttonService;

	private InlineKeyboardMarkup getInlineKeyboardMarkup(List<InlineKeyboardButton> inlineKeyboardButtonList) {
		var buttons = List.of(inlineKeyboardButtonList);
		return InlineKeyboardMarkup.builder().keyboard(buttons).build();
	}

	public InlineKeyboardMarkup getInlineKeyboardMarkup(String... buttonCallbackDatas) {
		return getInlineKeyboardMarkup(
				Arrays.stream(buttonCallbackDatas)
						.map(s -> buttonService.getNewButton(s))
						.collect(Collectors.toList()));
	}
}
