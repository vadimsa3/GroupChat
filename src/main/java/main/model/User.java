package main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity // сущность в БД
@Getter // из библиотеки lombok для создания объектов класса
@Setter // из библиотеки lombok для создания объектов класса
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // автоувеличение id на 1 при добавлении новых элементов в БД
    private int id;

    private String sessionId; // идентификатор приложения для сопоставления входящего юзера с юзером в БД

    @NotBlank // проверяет, что строка не пуста и не null
    private String name;
}
