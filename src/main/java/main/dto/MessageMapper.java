package main.dto;

import main.model.Message;

public class MessageMapper {
    public static DtoMessage map (Message message) {
        DtoMessage dtoMessage = new DtoMessage();
        // устанавливаем поля
        dtoMessage.setDateTime(message.getDateTime().toString());
        dtoMessage.setUserName(message.getUser().getName());
        dtoMessage.setText(message.getMessage());
        return dtoMessage;
    }
}
