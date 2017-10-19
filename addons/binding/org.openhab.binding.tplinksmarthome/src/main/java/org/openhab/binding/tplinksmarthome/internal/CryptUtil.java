/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.tplinksmarthome.internal;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Util class to encypt and decrypt data to be sent to and from the smart plug.
 *
 * @author Hilbrand Bouwkamp - Initial contribution
 */
public final class CryptUtil {

    private static final int KEY = 171;

    private CryptUtil() {
    }

    /**
     * Decrypt the byte array of the specified length. The length is necessary because the byte array might be larger
     * than the actual content.
     *
     * @param data byte array containing the data
     * @param length number of bytes in the byte array to read
     * @return decrypted String of the byte array
     * @throws IOException
     */
    public static String decrypt(byte[] data, int length) throws IOException {
        try (ByteArrayInputStream is = new ByteArrayInputStream(data)) {
            return decrypt(is, length);
        }
    }

    /**
     * Decrypt the byte data in the input stream. In the first 4 bytes the length of the data in the byte array is
     * coded.
     *
     * @param inputStream input stream containing length and data
     * @return decrypted String of the inputstream
     * @throws IOException
     */
    @NonNull
    public static String decryptWithLength(InputStream inputStream) throws IOException {
        try (DataInputStream is = new DataInputStream(inputStream)) {
            return decrypt(is, is.readInt());
        }
    }

    /**
     * Decrypt the byte data in the input stream with the length as given.
     *
     * @param inputStream input stream
     * @param length the number of bytes to read
     * @return decrypted String of the inputstream
     * @throws IOException
     */
    @SuppressWarnings("null")
    @NonNull
    private static String decrypt(InputStream inputStream, int length) throws IOException {
        StringBuilder sb = new StringBuilder();
        int in;
        int key = KEY;
        while (sb.length() < length && (in = inputStream.read()) != -1) {
            int nextKey = in;
            sb.append((char) (in ^ key));
            key = nextKey;
        }
        return sb.toString();
    }

    /**
     * Encrypts the string into a byte array with the first for bytes specifying the length of the given String. The
     * length is not encrypted.
     *
     * @param string String to encrypt
     * @return byte array with length and encrypted string
     */
    public static byte[] encryptWithLength(String string) {
        ByteBuffer bb = ByteBuffer.allocate(4 + string.length());
        bb.putInt(string.length());
        bb.put(encrypt(string));
        return bb.array();
    }

    /**
     * Encrypts the string into a byte array.
     *
     * @param string String to encrypt
     * @return byte array with encrypted string
     */
    public static byte[] encrypt(String string) {
        byte[] buffer = new byte[string.length()];
        byte key = (byte) KEY;
        for (int i = 0; i < string.length(); i++) {
            buffer[i] = (byte) (string.charAt(i) ^ key);
            key = buffer[i];
        }
        return buffer;
    }

}
