package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // сущность в БД
@Getter // из библиотеки lombok для создания объектов класса
@Setter // из библиотеки lombok для создания объектов класса
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // автоувеличение id на 1 при добавлении новых элементов в БД
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // связь т.к. много сообщений у одного юзера
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime dateTime;

    private String message;
}
