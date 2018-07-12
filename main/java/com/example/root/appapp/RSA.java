package com.example.root.appapp;

import java.math.BigInteger;
import java.util.Random;
public class RSA {

    private BigInteger p, q, n, m, e, d;
    private int bitlength = 1024;
    private Random r;
    private BigInteger one_;
    public RSA() {}
    public RSA(BigInteger p, BigInteger q) {
        this.p=p;
        this.q=q;
        n = p.multiply(q);
        m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }
    // Encrypt message
    public String encrypt(String message,BigInteger his_publc) {
        char[]msge=message.toCharArray();
        String re="";
        for (int i = 0; i < msge.length; i++) {
            int v=(int)msge[i];
            BigInteger x=new BigInteger(""+v);
            re+=(x.modPow(his_publc, n)).toString()+",";
        }
        return (re);
    }
    //func to get my private
    public BigInteger getprivate(BigInteger my_publc)
    {
        return my_publc.modInverse(m);
    }
    // Decrypt message
    public String decrypt(String message, BigInteger privt) {
        String m[]=message.split(",");
        String re="";
        int o=0;
        for (int i = 0; i < m.length; i++) {
            BigInteger b=new BigInteger(m[o]);
            re+=(char)(b.modPow(privt, n)).intValue();
            o++;
        }
        return (re);

    }


}
