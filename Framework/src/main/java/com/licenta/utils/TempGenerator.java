package com.licenta.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Lucian CONDESCU
 */
public class TempGenerator {
    private SecureRandom random = new SecureRandom();
    private static TempGenerator instance = new TempGenerator();

    public static TempGenerator getInstance() {
        return instance;
    }

    public String nextTemp() {
        return new BigInteger(130, random).toString(32);
    }

}
