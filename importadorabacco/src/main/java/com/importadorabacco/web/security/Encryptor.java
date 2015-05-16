package com.importadorabacco.web.security;

/**
 * Created by tanhengyi on 14-9-12.
 */
public interface Encryptor {
    /**
     * Encode a String with specified salt
     *
     * @param raw  plain text string
     * @param salt salt
     * @return encoded string
     */
    String encrypt(String raw, String salt);

    /**
     * Takes a previously encoded string and compares it with a raw string after mixing in the salt and
     * encoding that value
     *
     * @param encStr previously encoded string
     * @param rawStr plain text string
     * @param salt   salt
     * @return true or false
     */
    boolean isEncStrValid(String encStr, String rawStr, String salt);
}
