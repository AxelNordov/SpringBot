package ua.axel.springbot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.springbot.entity.Quiz;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

	@Query(value = "" +
			"SELECT * " +
			"FROM quiz " +
			"WHERE right_answer > 0 " +
			"ORDER BY random() " +
			"LIMIT 1;", nativeQuery = true)
	Quiz findRandomQuizWithRightAnswer();

}
