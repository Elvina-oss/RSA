package com.example.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ExtendedEuclideanAlgorithm {
    public static BigInteger[] Algorithm(BigInteger a, BigInteger b)
    {
        List<BigInteger[]> table = new ArrayList<>();
        BigInteger[] col_0 = new BigInteger[6];
        col_0[0] = a;
        col_0[1] = b;
        table.add(col_0);
        for(int i = 0;;i++)
        {
            table.get(i)[2] = table.get(i)[0].remainder(table.get(i)[1]);
            table.get(i)[3] = table.get(i)[0].divide(table.get(i)[1]);
            if(table.get(i)[2].equals(BigInteger.ZERO))
                break;
            BigInteger[] col = new BigInteger[6];
            col[0] = table.get(i)[1];
            col[1] = table.get(i)[2];
            table.add(col);
        }
        for(int i = table.size()-1; i>=0; i--)
        {
            if(i == table.size()-1)
            {
                table.get(i)[4] = BigInteger.ZERO;
                table.get(i)[5] = BigInteger.ONE;
            }
            else
            {
                table.get(i)[4] = table.get(i + 1)[5];
                table.get(i)[5] = table.get(i+1)[4].subtract(table.get(i+1)[5].multiply(table.get(i)[3]));
            }
        }
        BigInteger[] answer = new BigInteger[3];
        answer[0] = table.get(table.size() - 1)[1];
        answer[1] = table.get(0)[4];
        answer[2] = table.get(0)[5];
        return answer;
    }
}
