package uk.ac.qub.leaderelectiongame.game;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import uk.ac.qub.leaderelectiongame.consts.Consts;

/**
 * Class responsible for creating socket encrypting client - server communication. Extends socket.
 */
public class CipherSocket extends Socket {

    private static final String TAG = "CipherSocket";

    /**
     * Cipher algorithm name.
     */
    private String algorithm;

    /**
     * Cipher algorithm key.
     */
    private String key;

    /**
     * Constructor.
     * @param host
     * @param port
     * @param key
     * @throws IOException
     */
    public CipherSocket(String host, int port, String key)
            throws IOException {
        super(host, port);
        this.algorithm = Consts.CIPHER_ALGORITHM;
        this.key = key;
    }

    /**
     * Constructor.
     * @param key
     * @throws IOException
     */
    public CipherSocket(String key)
            throws IOException {
        this.algorithm = Consts.CIPHER_ALGORITHM;
        this.key = key;
    }

    /**
     * Socket input stream getter.
     * @return socket input stream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        InputStream is = super.getInputStream();
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(algorithm);
            int size = cipher.getBlockSize();
            byte[] tmp = new byte[size];
            Arrays.fill(tmp, (byte) 15);
            IvParameterSpec iv = new IvParameterSpec(tmp);
            SecretKeySpec secretKeySpec = new SecretKeySpec(generateAESKey(key), Consts.CIPHER_KEY_SPEC);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            is.close();
            throw new IOException("CipherSocket: Failed to init cipher - " + ex.getMessage());
        }
        CipherInputStream cis = new CipherInputStream(is, cipher);
        return cis;
    }

    /**
     * Socket output stream getter.
     * @return socket output stream
     * @throws IOException
     */
    public OutputStream getOutputStream() throws IOException {
        OutputStream os = super.getOutputStream();
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(algorithm);
            int size = cipher.getBlockSize();
            byte[] tmp = new byte[size];
            Arrays.fill(tmp, (byte) 15);
            IvParameterSpec iv = new IvParameterSpec(tmp);
            SecretKeySpec secretKeySpec = new SecretKeySpec(generateAESKey(key), Consts.CIPHER_KEY_SPEC);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        } catch (Exception ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            os.close();
            throw new IOException("CipherSocket: Failed to init cipher - " + ex.getMessage());
        }
        CipherOutputStream cos = new CipherOutputStream(os, cipher);
        return cos;
    }

    /**
     * Method to generate AES cipher key from text.
     * @param plainKey
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private byte[] generateAESKey(String plainKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (TextUtils.isEmpty(plainKey)) {
            return new byte[] {};
        }   //if
        byte[] key = plainKey.getBytes("UTF-8");
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        key = sha1.digest(key);
        return Arrays.copyOf(key, 16);
    }
}
