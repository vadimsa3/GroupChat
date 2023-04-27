package main;

import jakarta.validation.Valid;
import main.dto.DtoMessage;
import main.dto.MessageMapper;
import main.model.Message;
import main.model.MessageRepository;
import main.model.User;
import main.model.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    // автоматическое подключение репозитория к контроллеру
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private MessageMapper messageMapper;

    // экшен авторизации (получает из приложения sessionId, проверяет в БД таблицы пользователь,
    // если найдет - то отвечает result true
    @GetMapping("/init") // на Get запрос init в командной строке вернет
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>(); // создаем перечень ответов
        // TODO: проверка авторизации пользователя, check sessionId. If found => true, If note => false
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId(); // получаем sessionId юзера
        Optional<User> userOptional = userRepository.findBySessionId(sessionId); // создаем нового пользователя с sessionId
        response.put("result", userOptional.isPresent()); // isPresent() - вернет или true или false
        // если true, то пользователь существует и авторизован, если false, то авторизация
        return response; // будет отображаться в виде json
    }

    // экшен авторизации пользователя
    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@Valid @RequestParam String name) { // + валидация входных параметров
        HashMap<String, Boolean> response = new HashMap<>(); // создаем перечень ответов
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId(); // получаем sessionId юзера
        User user = new User(); // создаем нового пользователя
        user.setName(name); // присваиваем пользователю имя
        user.setSessionId(sessionId); // присваиваем юзеру полученный sessionId
        userRepository.save(user); // добавляем пользователя в БД посредством репозитория
        response.put("result", true);
        return response; // будет отображаться в виде json
    }

    // экшен отправки сообщений - отправляем сообщение в БД
    @PostMapping("/message")
    public Map<Object, Object> sendMessage(@RequestParam String message) {
        HashMap<String, Boolean> response = new HashMap<>(); // создаем перечень ответов
        if (Strings.isEmpty(message)) {
            // если сообщение пустое, то выводит сообщение с фронта
            return Map.of("result", false); // статический метод вернет результат
        }
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId(); // получаем sessionId юзера
        User user = userRepository.findBySessionId(sessionId).get(); // берем пользователя, который пишет сообщение
        Message newMessage = new Message(); // создаем сообщение
        newMessage.setDateTime(LocalDateTime.now()); // время сообщения
        newMessage.setMessage(message); // текст сообщения
        newMessage.setUser(user); // имя пользователя
        messageRepository.save(newMessage); // отправляем в БД сообщение
        return Map.of("result", true);
    }
    // экшен перечня сообщений - получаем список сообщений
    @GetMapping("/message")
    public List<DtoMessage> getMessagesList() {
        // получаем все сообщения из БД с сортировкой в базе
        return messageRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime")).stream()
                .map(MessageMapper::map)
                .collect(Collectors.toList());
    }

    // экшен списка пользователей - получаем список пользователей
    @GetMapping("/user")
    public HashMap<Integer, String> getUsersList(){
        return null;
    }
}
