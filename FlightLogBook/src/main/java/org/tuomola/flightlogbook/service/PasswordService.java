package org.tuomola.flightlogbook.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

/**
 *
 * @author ptuomola
 */

@Service
public class PasswordService {
    
    private Pattern pattern;
    private Matcher matcher;
 
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
 
    public PasswordService() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public String encrypt(String password) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), "1234".getBytes(), 10000, 512);
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded();
            return Hex.encodeHexString(res);
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }    
    }
    
    public boolean isValid(String password) {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
