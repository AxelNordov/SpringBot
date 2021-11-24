package ua.axel.springbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz")
public class Quiz {

	@Id
	private Long id;

	private String question;

	private String subject;

	private String subSubject;

	private String topic;

	private Integer originalQuestionNumber;

	private Byte rightAnswer;

	private Boolean oneAnswer;

	private String description;

	@Column(name = "link_1")
	private String link1;

	@Column(name = "link_2")
	private String link2;

	private String linkSource;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "quiz")
	private List<Answer> answers;

}
