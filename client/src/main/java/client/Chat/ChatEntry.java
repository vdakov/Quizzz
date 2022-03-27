package client.Chat;

import javafx.scene.image.ImageView;

public class ChatEntry {

    private MessageType type;
    private ImageView content;
    private String userName;


    public enum MessageType {
        EMOJI
    }

    public ChatEntry(String userName, ImageView content)
    {
        this.userName = userName;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public ImageView getContent() {
        return content;
    }

    public void setContent(ImageView content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setSender(String userName) {
        this.userName = userName;
    }
}
