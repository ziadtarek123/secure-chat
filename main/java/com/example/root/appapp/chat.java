package com.example.root.appapp;

/**
 * Created by root on 1/27/18.
 */

public class chat {
    String message;
    String msg_id;
    String my_id;
    String user_id;
    String type;
    String datetime;
    String k;

    public chat() {

    }

    public chat(String message, String msg_id, String my_id, String user_id,String type,String datetime,String k) {
        this.message = message;
        this.msg_id = msg_id;
        this.my_id = my_id;
        this.user_id = user_id;
        this.datetime=datetime;
        this.type=type;
        this.k=k;
    }

    public String getType() {
        return type;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getMessage() {
        return message;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getMy_id() {
        return my_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getK() {
        return k;
    }
}
