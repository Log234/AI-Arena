package Communication;

import Message.Message;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.logging.Logger;

public class MessageHandler {
    Gson json;
    Logger log;

    MessageHandler(Logger log){
        this.log = log;
        this.json = new Gson();
    }

    public String sendMessage(Message msg, Type type) {
        return json.toJson(msg, type);
    }

    public <T extends Message> T receiveMessage(Type type) {
        return null;
    }
}
