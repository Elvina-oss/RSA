package com.example.rsa;

public class Alphabet {
    public static String al;
    public static void defineAlphabet(String lang)
    {
        if(lang.equals("Русский"))
            al="абвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789";
        else
            al = "abcdefghijklmnopqrstuvwxyz0123456789";
    }

    public static boolean checkString(String text)
    {
        for(char c: text.toCharArray())
            if (al.indexOf(c) == -1)
                return false;
        return true;
    }
}
