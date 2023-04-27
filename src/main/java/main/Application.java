package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // указывает приложению на место запуска
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // запуск приложения
    }
}
