package stuff;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash implements Constants
{

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String getHash(String password)
    {
        byte[] hash;
        try { hash = getSHA(password); }
        catch(NoSuchAlgorithmException e) { return null; }
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < Constants.maxPasswordLength)
            hexString.insert(0, '0');
        return hexString.toString();
    }

}
