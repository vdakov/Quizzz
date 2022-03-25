package commons.Chat;

import javax.swing.text.html.ImageView;

public class ChatEntry {

    private MessageType type;
    private ImageView content;
    private String userName;

    public enum MessageType {
        EMOJI,
        JOKER
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
