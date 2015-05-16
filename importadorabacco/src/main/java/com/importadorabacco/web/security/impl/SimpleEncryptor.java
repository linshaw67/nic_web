package com.importadorabacco.web.security.impl;

import com.importadorabacco.web.security.Encryptor;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by tanhengyi on 14-9-12.
 */
@Component
public class SimpleEncryptor implements Encryptor {
    private static final String ALGORITHM = "SHA-256";
    private static final MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder(ALGORITHM, true);

    @Override
    public String encrypt(String raw, String salt) {
        return encoder.encodePassword(raw, salt);
    }

    @Override
    public boolean isEncStrValid(String encStr, String rawStr, String salt) {
        return encoder.isPasswordValid(encStr, rawStr, salt);
    }
}
