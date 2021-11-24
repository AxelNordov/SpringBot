package ua.axel.springbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
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

}
