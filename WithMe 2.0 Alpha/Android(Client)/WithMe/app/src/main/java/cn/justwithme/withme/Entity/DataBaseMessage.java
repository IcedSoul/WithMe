package cn.justwithme.withme.Entity;

/**
 * Created by 14437 on 2017/2/19.
 */

public class DataBaseMessage {
    private int id;
    private int from;
    private int to;
    private String content;
    private int type;
    private String time;
    private int isTransport;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsTransport() {
        return isTransport;
    }

    public void setIsTransport(int isTransport) {
        this.isTransport = isTransport;
    }
}
