package client.Chat;

import javafx.scene.image.ImageView;

public class ChatEntry {

    private ImageView content;
    private String userName;


    public ChatEntry(String userName, ImageView content)
    {
        this.userName = userName;
        this.content = content;
    }

    public ChatEntry(String userName)
    {
        this.userName = userName;
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
