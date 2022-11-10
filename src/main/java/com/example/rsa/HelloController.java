package com.example.rsa;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

public class HelloController {
    @FXML
    TextField expBase, expPower, expMod, expRem;
    @FXML
    Label expError;
    @FXML
    TextField euclidA, euclidB, euclidD, euclidX, euclidY;
    @FXML
    Label euclidError, rsaError;
    @FXML
    TextField rsaSize, rsaP, rsaQ, rsaE, rsaFi, rsaD, rsaN;
    @FXML
    TextArea textIn, textOut, textBack;
    @FXML
    Button language;
    @FXML
    public void expMod()
    {
        expError.setText("");
        BigInteger base, power, mod;
        if(expBase.getText().equals("") || expPower.getText().equals("") || expMod.getText().equals(""))
        {
            expError.setText("Есть пустые поля");
            return;
        }
        try
        {
            base = new BigInteger(expBase.getText());
            power = new BigInteger(expPower.getText());
            mod = new BigInteger(expMod.getText());
        } catch (NumberFormatException e)
        {
            expError.setText("Можно использовать только цифры");
            return;
        }
        if (base.compareTo(BigInteger.ZERO)<=0 ||power.compareTo(BigInteger.ZERO)<=0
                ||mod.compareTo(BigInteger.ZERO)<=0)
        {
            expError.setText("Могут быть использованы только натуральные числа");
            return;
        }
        expRem.setText(ModularExponentiation.Algorithm(base, power, mod).toString());
    }
    @FXML
    public void euclidIs()
    {
        euclidError.setText("");
        BigInteger a, b;
        if(euclidA.getText().equals("") || euclidB.getText().equals(""))
        {
            euclidError.setText("Есть пустые поля");
            return;
        }
        try
        {
            a = new BigInteger(euclidA.getText());
            b = new BigInteger(euclidB.getText());
        }
        catch (NumberFormatException e)
        {
            euclidError.setText("Можно использовать только цифры");
            return;
        }
        if (a.compareTo(BigInteger.ZERO)<=0 ||b.compareTo(BigInteger.ZERO)<=0)
        {
            euclidError.setText("Могут быть использованы только натуральные числа");
            return;
        }
        BigInteger[] euclidDeicision = ExtendedEuclideanAlgorithm.Algorithm(a, b);
        euclidD.setText(euclidDeicision[0].toString());
        euclidX.setText(euclidDeicision[1].toString());
        euclidY.setText(euclidDeicision[2].toString());
    }
    @FXML
    public void generate()
    {
        rsaError.setText("");
        int bitLen;
        try {
            bitLen = Integer.parseInt(rsaSize.getText());
        }
        catch (NumberFormatException e)
        {
            rsaError.setText("Битность состоит из цифр");
            return;
        }
        if(bitLen<8 || bitLen >2048)
        {
            rsaError.setText("Введите битность в промежутке от 8 до 2048");
            return;
        }
        BigInteger[] pq = Generation.generatePQ(bitLen);
        rsaP.setText(pq[0].toString());
        rsaQ.setText(pq[1].toString());
        List<BigInteger> keys = RSA.generationKeys(pq[0], pq[1], bitLen);
        rsaN.setText(keys.get(0).toString());
        rsaFi.setText(keys.get(1).toString());
        rsaE.setText(keys.get(2).toString());
        rsaD.setText(keys.get(3).toString());
    }
    @FXML
    public void changeLanguage()
    {
        if(language.getText().equals("Русский"))
            language.setText("English");
        else
            language.setText("Русский");
    }
    @FXML
    public void encrypt()
    {
        Alphabet.defineAlphabet(language.getText());
        rsaError.setText("");
        if(rsaP.getText().equals("") || rsaQ.getText().equals("") ||
                rsaE.getText().equals("") || rsaN.getText().equals("") || rsaD.getText().equals(""))
        {
            rsaError.setText("Cгенерируйте ключ");
            return;
        }
        if(textIn.getText().equals(""))
        {
            rsaError.setText("Пустая строка");
            return;
        }
        if(!Alphabet.checkString(textIn.getText().toLowerCase(Locale.ROOT)))
        {
            rsaError.setText("В тексте могут содержаться только символы, входящие в алфавит");
            return;
        }
        try {
            String encrypted = RSA.encrypt(
                    textIn.getText(), new BigInteger(rsaE.getText()), new BigInteger(rsaN.getText())
            );
            textOut.setText(encrypted);
        }
        catch(RuntimeException e)
        {
            rsaError.setText("Для данного текста следует использовать бОльшую битность");
        }

    }
    @FXML
    public void decode()
    {
        Alphabet.defineAlphabet(language.getText());
        rsaError.setText("");
        if(rsaP.getText().equals(""))
        {
            rsaError.setText("Cгенерируйте ключ");
            return;
        }
        if(textOut.getText().equals(""))
        {
            rsaError.setText("Пустая строка");
            return;
        }
        try {
            BigInteger bigTest = new BigInteger(textOut.getText());
        }
        catch (NumberFormatException e)
        {
            rsaError.setText("В криптограмме должны быть только цифры");
            return;
        }
        String decoded = RSA.decode(
                textOut.getText(), new BigInteger(rsaD.getText()), new BigInteger(rsaN.getText())
        );
        textBack.setText(decoded);
    }
    @FXML
    public void clear()
    {
        rsaP.setText("");
        rsaQ.setText("");
        rsaN.setText("");
        rsaD.setText("");
        rsaFi.setText("");
        rsaE.setText("");
        textIn.setText("");
        textOut.setText("");
        textBack.setText("");
        rsaError.setText("");
    }
}