package ua.axel.springbot.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Quiz quiz = (Quiz) o;
		return id != null && Objects.equals(id, quiz.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
