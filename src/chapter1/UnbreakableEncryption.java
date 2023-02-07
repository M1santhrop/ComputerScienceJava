package chapter1;

import java.util.Random;

public class UnbreakableEncryption {

    private static byte[] randomKey(int length) {
        byte[] dummy = new byte[length];
        Random random = new Random();
        random.nextBytes(dummy);
        return dummy;
    }
    
    public static KeyPair encrypt(String original) {
        byte[] originalBytes = original.getBytes();
        byte[] dummyKey = randomKey(originalBytes.length);
        byte[] encryptedKey = new byte[originalBytes.length];
        for (int i = 0; i < originalBytes.length; i++) {
            encryptedKey[i] = (byte) (originalBytes[i] ^ dummyKey[i]);
        }
        return new KeyPair(dummyKey, encryptedKey);
    }
    
    public static String decrypt(KeyPair keyPair) {
        byte[] dummyKey = keyPair.key1;
        byte[] encryptedKey = keyPair.key2;
        byte[] decrypted = new byte[dummyKey.length];
        for (int i = 0; i < dummyKey.length; i++) {
            decrypted[i] = (byte) (dummyKey[i] ^ encryptedKey[i]);
        }
        return new String(decrypted);
    }

    public static void main(String[] args) {
        String original = "One Time Pad!";
        KeyPair keyPair = encrypt(original);
        String decryptedString = decrypt(keyPair);
        System.out.println(decryptedString);
    }
}
