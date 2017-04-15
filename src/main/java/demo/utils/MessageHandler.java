package demo.utils;

import lombok.Data;

/**
 * Created by KozuePC on 4/14/2017.
 */
@Data
public class MessageHandler {

    public enum MessageType{
        INFO, WARNING, SUCCESS, ERROR
    }

    private String title;
    private MessageType messageType;
    private String message;


    public MessageHandler() {
    }

    public MessageHandler(MessageType messageType, String message) {
        this.message = message;
        this.messageType = messageType;
    }

    public MessageHandler(String title, String message, MessageType messageType) {
        this.title = title;
        this.message = message;
        this.messageType = messageType;
    }
}
