package ua.axel.springbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.axel.springbot.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
