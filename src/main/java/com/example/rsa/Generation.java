package com.example.rsa;

import com.google.common.math.BigIntegerMath;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

public class Generation {
    public static BigInteger[] generatePQ(int size)
    {
        BigInteger[] pq = new BigInteger[2];
        pq[0] = generate(size);
        pq[1] = generate(size);
        while (pq[1].equals(pq[0]))
            pq[1] = generate(size);
        return pq;
    }
    public static BigInteger generate(int size)
    {
        Random r = new Random();
        BigInteger n;
        boolean firstCheck;
        boolean secondCheck = false;
        do{
           n = new BigInteger(size, r);
           firstCheck = checkOnPrimeNumbers(n);
           if (firstCheck)
               secondCheck = SolovayStrassen(n);
        } while(!secondCheck);
        return n;
    }
    public static boolean SolovayStrassen(BigInteger n)
    {
        int bitnost = n.bitCount();
        int i;
        Random rand = new Random();
        for(i = 0; i < BigIntegerMath.log2(n, RoundingMode.UP); i++)
        {
            BigInteger a;
            do{
                a = new BigInteger(bitnost, rand);
            } while(a.compareTo(n)>=0 || a.compareTo(BigInteger.TWO)<0);
            if(ExtendedEuclideanAlgorithm.Algorithm(a, n)[0].compareTo(BigInteger.ONE) > 0)
                return false;
            BigInteger legendre = Jacobi(a, n).add(n).remainder(n);
            BigInteger mod = a.modPow(n.subtract(BigInteger.ONE).divide(BigInteger.TWO), n);
            if(!mod.equals(legendre))
                return false;
        }
        return true;
    }

    public static BigInteger Jacobi(BigInteger a, BigInteger n)
    {
        BigInteger t = BigInteger.ONE;
        while (!a.equals(BigInteger.ZERO)){
            while (a.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                a = a.divide(BigInteger.TWO);
                if (n.mod(new BigInteger("8")).equals(new BigInteger("3")) ||
                        n.mod(new BigInteger("8")).equals(new BigInteger("5")))
                    t = t.multiply(new BigInteger("-1"));
            }
            BigInteger temp = n;
            n = a;
            a = temp;

            if (a.mod(new BigInteger("4")).equals(new BigInteger("3")) &&
                    n.mod(new BigInteger("4")).equals(new BigInteger("3")))
                t = t.multiply(new BigInteger("-1"));
            a = a.mod(n);
        }
        if(n.equals(BigInteger.ONE))
            return t;
        else
            return BigInteger.ZERO;
    }

    public static BigInteger generateE(int size, BigInteger fi)
    {
        Random r = new Random();
        BigInteger e;
        do{
            e= new BigInteger(size, r);
        } while (ExtendedEuclideanAlgorithm.Algorithm(e, fi)[0].compareTo(BigInteger.ONE)!=0 ||
        e.compareTo(fi)>=0 || e.compareTo(BigInteger.TWO) < 0);
        return e;
    }

   public static boolean checkOnPrimeNumbers(BigInteger n)
   {
       if(n.equals(BigInteger.ONE) || n.equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("2")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("3")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("5")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("7")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("11")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("13")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("17")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("19")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("23")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("29")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("31")).equals(BigInteger.ZERO))
           return false;
       if(n.remainder(new BigInteger("37")).equals(BigInteger.ZERO))
           return false;
       return true;
   }
}
