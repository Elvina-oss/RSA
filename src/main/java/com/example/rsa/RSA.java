package com.example.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    public static String encrypt(String text, BigInteger e, BigInteger n)
    {
        String binaryString = stringToBinary(text);
        BigInteger h = new BigInteger(binaryString, 2);
        if (h.compareTo(n)>0)
            throw new RuntimeException();
        BigInteger result = ModularExponentiation.Algorithm(h, e, n);
        return result.toString();
    }

    public static String decode(String text, BigInteger d, BigInteger n)
    {
        BigInteger c = ModularExponentiation.Algorithm(new BigInteger(text), d, n);
        return binaryToString(c.toString(2));
    }



    public static List<BigInteger> generationKeys(BigInteger p, BigInteger q, int size)
    {
        List<BigInteger> keys = new ArrayList<>();
        BigInteger n = p.multiply(q);
        keys.add(n);
        BigInteger fi = (p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE))));
        keys.add(fi);
        BigInteger e = Generation.generateE(size * 2/3, fi);
        keys.add(e);
        BigInteger d = (ExtendedEuclideanAlgorithm.Algorithm(fi, e)[2].add(fi)).mod(fi);
        keys.add(d);
        return keys;

    }


        public static String stringToBinary(String text) {
            StringBuilder bin = new StringBuilder();
            for(int i = 0; i < text.length(); i++)
            {
                StringBuilder binaryCh = new StringBuilder(
                        Integer.toBinaryString(Alphabet.al.indexOf(text.charAt(i)) + 1));
                int addNules = Integer.toBinaryString(Alphabet.al.length() + 1).length() - binaryCh.length();
                for(int j = 0;j < addNules; j++)
                    binaryCh.insert(0, "0");
                bin.append(binaryCh);
            }
            return  bin.toString();
    }

    public static String binaryToString(String firstBin) {
        StringBuilder binary = new StringBuilder(firstBin);
        int power = Integer.toBinaryString(Alphabet.al.length()).length();
        int addNules = power - firstBin.length() % power;
        for(int i = 0; i < addNules; i++)
            binary.insert(0, '0');
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < binary.length(); i+=power)
        {
            int a = Integer.parseInt(binary.substring(i, i+power), 2) % Alphabet.al.length();
            result.append(Alphabet.al.charAt((a + Alphabet.al.length() - 1) % Alphabet.al.length()));
        }
        return result.toString();
    }
}
