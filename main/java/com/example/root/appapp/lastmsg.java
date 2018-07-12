package com.example.root.appapp;

/**
 * Created by mohamed on 14/02/18.
 */

public class lastmsg
{
    private String lastmsg;
    private String lm__id;
    private String seen;

    public lastmsg(String lastmsg, String lm__id,String seen) {
        this.lastmsg = lastmsg;
        this.lm__id = lm__id;
        this.seen=seen;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public String getSeen() {
        return seen;
    }

    public String getLm__id() {
        return lm__id;
    }
}
