package cn.justwithme.withme.Entity;

/**
 * Created by 14437 on 2017/2/18.
 */

public class MessageShow {
    public  MessageShow(){

    }
    public  MessageShow(int messageType,int messageStyle,String contents,String time){
        this.messageType = messageType;
        this.messageStyle = messageStyle;
        this.contents = contents;
        this.time = time;
    }
    private int messageType;
    private int messageStyle;
    private String contents;
    private String time;

    public int getMessageType() {
        return messageType;

    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageStyle() {
        return messageStyle;
    }

    public void setMessageStyle(int messageStyle) {
        this.messageStyle = messageStyle;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
