package Message;

public abstract class Message {
    private MessageType type;

    Message(MessageType type) {
        this.type = type;
    }

}
