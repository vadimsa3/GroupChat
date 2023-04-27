package main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// для правильного формирования json
public class DtoMessage {
    private String text;
    private String dateTime;
    private String userName;

    public DtoMessage() {
    }

    public DtoMessage(String text, String dateTime, String userName) {
        this.text = text;
        this.dateTime = dateTime;
        this.userName = userName;
    }
}
