package com.example.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ModularExponentiation {
    public static BigInteger Algorithm(BigInteger base, BigInteger power, BigInteger mod)
    {
        String b = power.toString(2);
        BigInteger[] a = new BigInteger[b.length()];
        a[0] = base;
        for(int i = 1; i < b.length(); i++)
            if(b.charAt(i) == '0')
                a[i] = a[i-1].pow(2).remainder(mod);
            else
                a[i] = a[i-1].pow(2).multiply(base).remainder(mod);
            return a[b.length()-1];
    }
}



