package ua.axel.springbot.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer {

	@Id
	private Long id;

	@Column(name = "order_number")
	private Byte orderNumber;

	@Column(name = "text")
	private String text;

	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Answer answer = (Answer) o;
		return id != null && Objects.equals(id, answer.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
