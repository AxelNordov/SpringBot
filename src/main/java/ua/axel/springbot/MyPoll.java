package ua.axel.springbot;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import ua.axel.springbot.entity.Quiz;

@Data
@AllArgsConstructor
public class MyPoll {

	private SendPoll sendPoll;

	private Quiz quiz;

}
