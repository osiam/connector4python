/*
 * Copyright (C) 2012 OSIAM GmbH <info@org.osiam.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.osiam.ng.hash;


import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * With this class you can create salt for hashes and hashes.
 * <p/>
 * Salts are used to complicate a rainbow table lookup @link{http://en.wikipedia.org/wiki/Rainbow_table}.
 * <p/>
 * Hashes are calculated in SHA-512.
 */
public class CalculateHash {

    public static CalculateHash instance = new CalculateHash();

    CalculateHash() {
    }

    public static String calculate(String input) {
        try {
            return instance.calculateHash(input);
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred while trying to calculate the hash value of " + input, e);
        }
    }


    private static final String CHARSET_NAME = "UTF-8";
    public static final int ALGOR_BIT_SIZE = 512;
    private static final String ALGORITHM = "SHA-" + ALGOR_BIT_SIZE;


    String calculateHash(String input) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.update(input.getBytes(CHARSET_NAME), 0, input.length());
        return new String(Base64.encodeBase64(md.digest()));
    }

    String _calculateSalt() {
        Random r = new SecureRandom();
        byte[] salt = new byte[20];
        r.nextBytes(salt);
        return new String(Base64.encodeBase64(salt));
    }

    /**
     * This method combines a given salt and a given password with
     * salt + passwordhash
     * and calculate a new hash out of it.
     *
     * @param salt,         the salt to combine
     * @param passwordhash, a hash of a password to be combined with the salt
     * @return a SHA-512 hash of salt + passwordhash
     */
    public static String calculateWithSalt(String salt, String passwordhash) {
        String input = salt + passwordhash;
        return CalculateHash.calculate(input);
    }

    /**
     * Generates a 20 byte salt with SecureRandom
     *
     * @return 20 bytes of random numbers.
     */
    public static String calculateSalt() {
        return instance._calculateSalt();
    }
}
