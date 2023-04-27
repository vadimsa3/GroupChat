package main.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// для доступа к БД и сохранения созданного пользователя в БД
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // метод поиска в таблице user по полю sessionId
    Optional<User> findBySessionId(String sessionId);
}
