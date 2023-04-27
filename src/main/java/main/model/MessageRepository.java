package main.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// для доступа к БД и сохранения сообщений
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

//    // метод поиска в таблице user по полю sessionId
//    Optional<User> findBySessionId(String sessionId);

}
