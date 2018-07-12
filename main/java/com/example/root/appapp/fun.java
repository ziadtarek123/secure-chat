package com.example.root.appapp;

import java.security.SecureRandom;

public class fun {
    public  String rand_txt(int len) {
        SecureRandom rand=new SecureRandom();
        String AB="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&(*)_+?><";
        StringBuilder sb=new StringBuilder(len);
        for (int i=0;i<len;i++)
        {
            sb.append(AB.charAt(rand.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
