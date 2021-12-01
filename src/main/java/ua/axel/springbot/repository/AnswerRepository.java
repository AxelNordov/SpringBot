package ua.axel.springbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.springbot.entity.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

}
