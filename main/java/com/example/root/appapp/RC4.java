package com.example.root.appapp;

public class RC4 {
    private String plain_text;//xor with k
    private String key;//my key will be stored in this varable to make operathons
    private char[]key_array;//the same key but in array
    private char[]plain_array;//the plain text in array
    private int[] s=new int[256];//s[0,1,2,............,255]
    private char[] t=new char[256];//t[k,e,y,k,e,y,k,........k]
    private int[] k;//xor with plaintext
    private String ciphertext="";//variable to store cipertext
    private int[] cipher_array;//same ciphertext but in array
    private String encrypted="";//encrypted plaintext

    public String encryption(String plain_text,String key)//methode to encryption
    {
        this.plain_text=plain_text;
        plain_array=plain_text.toCharArray();

        int length_plain=plain_array.length;
        cipher_array=new int[length_plain];

        generate_key(key,length_plain);
        //Encryption
        String de="";
        encrypted="";
        for(int a=0;a<length_plain;a++)//to make plaintext XOR with K
        {
            encrypted+=(char)((int)k[a]^(int)plain_array[a]);
        }
        return encrypted;//return encrypted plaintext
    }

    private void generate_key(String key,int length_plain)//method to produce K
    {
        this.key=key;
        key_array=key.toCharArray();
        int length_key=key_array.length;
        //initialization
        String vg="";
        for(int i=0;i<256;i++)
        {
            s[i]=i;
            t[i]=key_array[i%length_key];
            vg+=t[i];
        }
        //KSA
        int j=0;

        for(int i=0;i<256;i++)
        {
            j=(j+s[i]+(int)t[i])%256;
            int temp=s[i];
            s[i]=s[j];
            s[j]=temp;
        }

        //PRGA
        int i=0;j=0;int lp=length_plain;int counter=0;k=new int[lp];
        while(lp>0)
        {

            i=(i+1)%256;//2
            j=(j+s[i])%256;//1
            //swapping
            int temp2=s[i];
            s[i]=s[j];
            s[j]=temp2;
            k[counter]=s[(s[i]+s[j])%256];
            lp--;
            counter++;
        }

    }
}
