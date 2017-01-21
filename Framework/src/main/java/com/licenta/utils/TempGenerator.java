package com.licenta.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Lucian CONDESCU
 */
public enum TempGenerator {
    INSTANCE;
    private SecureRandom random = new SecureRandom();

    public String nextTemp() {
        return new BigInteger(130, random).toString(32);
    }

}
