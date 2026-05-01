package com.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.symmetric.SM4;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

public class EncryptUtil {

    private static final String DES_ALGORITHM = "DES";
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String AES_KEY = "1234567890123456";
    private static final String AES_IV = "abcdefghijklmnop";

    private static final byte[] SM4_KEY = hexStringToBytes("0123456789abcdef0123456789abcdef");

    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("无效的十六进制字符串");
        }
        int length = hexString.length() / 2;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    /**
     * md5算法
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        if (text == null) return null;
        String md5str = DigestUtil.md5Hex(text);
        return md5str;
    }

    /**
     * SHA-256算法
     *
     * @param text
     * @return
     */
    public static String sha256(String text) {
        if (text == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(text.getBytes());
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * SM3国密哈希算法
     * SM3是中国国家密码管理局发布的密码杂凑算法标准
     *
     * @param text 明文
     * @return 密文（64位十六进制字符串）
     */
    public static String sm3(String text) {
        if (text == null) return null;
        String sm3str = SmUtil.sm3(text);
        return sm3str;
    }

    /**
     * SM3国密哈希算法（字节数组输入）
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] sm3(byte[] data) {
        if (data == null) return null;
        SM3 sm3 = new SM3();
        return sm3.digest(data);
    }

    /**
     * SM4国密对称加密算法
     * SM4是中国国家密码管理局发布的分组密码算法标准
     * 采用CBC模式，PKCS5Padding填充
     *
     * @param text 明文
     * @return 密文（Base64编码字符串）
     */
    public static String sm4Encrypt(String text) {
        if (text == null) return null;
        try {
            SM4 sm4 = new SM4(SM4_KEY);
            byte[] encryptedData = sm4.encrypt(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SM4国密对称加密算法（字节数组输入）
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] sm4Encrypt(byte[] data) {
        if (data == null) return null;
        try {
            SM4 sm4 = new SM4(SM4_KEY);
            return sm4.encrypt(data);
        } catch (Exception e) {
            throw new RuntimeException("SM4 加密失败", e);
        }
    }

    /**
     * SM4国密对称解密算法
     *
     * @param text 密文（Base64编码字符串）
     * @return 明文
     */
    public static String sm4Decrypt(String text) {
        if (text == null) return null;
        try {
            SM4 sm4 = new SM4(SM4_KEY);
            byte[] decodedData = Base64.getDecoder().decode(text);
            byte[] decryptedData = sm4.decrypt(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SM4国密对称解密算法（字节数组输入）
     *
     * @param cipherText 密文字节数组
     * @return 明文字节数组
     */
    public static byte[] sm4Decrypt(byte[] cipherText) {
        if (cipherText == null) return null;
        try {
            SM4 sm4 = new SM4(SM4_KEY);
            return sm4.decrypt(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("SM4 解密失败", e);
        }
    }

    /**
     * DES加密
     */
    public static String desEncrypt(String text) {
        if (text == null) return null;
        try {
            String key = "12345678";
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES加密字节数组
     */
    public static byte[] desEncrypt(byte[] data) {
        try {
            String key = "12345678";
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("DES 加密失败", e);
        }
    }

    /**
     * DES解密
     */
    public static String desDecrypt(String text) {
        if (text == null) return null;
        try {
            String key = "12345678";
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            byte[] decodedData = Base64.getDecoder().decode(text);
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密字节数组
     */
    public static byte[] desDecrypt(byte[] cipherText) {
        try {
            String key = "12345678";
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("DES 解密失败", e);
        }
    }

    /**
     * AES加密
     */
    public static String aesEncrypt(String text) {
        if (text == null) return null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AES_IV.getBytes());
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedData = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesEncrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES 加密失败", e);
        }
    }


    /**
     * AES解密
     */
    public static String aesDecrypt(String text) {
        if (text == null) return null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AES_IV.getBytes());
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedData = Base64.getDecoder().decode(text);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesDecrypt(byte[] cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("AES 解密失败", e);
        }
    }

    /**
     * RSA公钥加密字节数组
     */
    public static byte[] rsaEncrypt(byte[] data) {
        PublicKey publicKey = RSAKeyManager.getPublicKey();
        System.out.println("[RSA加密] 使用RSA公钥加密数据，数据长度: " + data.length + " 字节");
        return RSAUtil.encrypt(data, publicKey);
    }

    /**
     * RSA私钥解密字节数组
     */
    public static byte[] rsaDecrypt(byte[] cipherText) {
        PrivateKey privateKey = RSAKeyManager.getPrivateKey();
        System.out.println("[RSA解密] 使用RSA私钥解密数据，密文长度: " + cipherText.length + " 字节");
        return RSAUtil.decrypt(cipherText, privateKey);
    }

    /**
     * RSA字符串加密
     */
    public static String rsaEncrypt(String text) {
        PublicKey publicKey = RSAKeyManager.getPublicKey();
        System.out.println("[RSA加密] 使用RSA公钥加密字符串，原文长度: " + text.length() + " 字符");
        return RSAUtil.encrypt(text, publicKey);
    }

    /**
     * RSA字符串解密
     */
    public static String rsaDecrypt(String text) {
        PrivateKey privateKey = RSAKeyManager.getPrivateKey();
        System.out.println("[RSA解密] 使用RSA私钥解密字符串，密文长度: " + text.length() + " 字符");
        return RSAUtil.decrypt(text, privateKey);
    }

    /**
     * 混合加密：AES加密数据 + RSA加密AES密钥
     * 适用于加密较长文本，密文更短且安全
     *
     * @param text 待加密的明文
     * @return 格式：Base64(RSA加密的AES密钥+IV) + ":" + Base64(AES加密的数据)
     */
    public static String hybridEncrypt(String text) {
        if (text == null) return null;
        try {
            // 1. 生成随机AES密钥（16字节）
            SecureRandom secureRandom = new SecureRandom();
            byte[] aesKey = new byte[16];
            secureRandom.nextBytes(aesKey);

            // 2. 生成随机IV（16字节）
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);

            // 3. 使用AES加密数据
            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            aesCipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptedData = aesCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            // 4. 使用RSA加密AES密钥和IV
            PublicKey publicKey = RSAKeyManager.getPublicKey();
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 将AES密钥和IV组合后加密（16+16=32字节 < 245字节限制）
            byte[] keyAndIV = new byte[32];
            System.arraycopy(aesKey, 0, keyAndIV, 0, 16);
            System.arraycopy(iv, 0, keyAndIV, 16, 16);
            byte[] encryptedKey = rsaCipher.doFinal(keyAndIV);

            // 5. 组合结果：RSA加密的密钥 + ":" + AES加密的数据
            String result = Base64.getEncoder().encodeToString(encryptedKey) +
                    ":" +
                    Base64.getEncoder().encodeToString(encryptedData);

            System.out.println("[混合加密：AES加密数据 + RSA加密AES密钥] 原文长度: " + text.length() + ", 密文长度: " + result.length() + ", 加密结果：" + result);
            return result;

        } catch (Exception e) {
            System.err.println("[混合加密：AES加密数据 + RSA加密AES密钥] 加密失败: " + e.getMessage());
            throw new RuntimeException("混合加密失败", e);
        }
    }

    /**
     * 混合解密：RSA解密AES密钥 + AES解密数据
     *
     * @param encryptedText 混合格式的密文（格式：Base64(RSA密钥) + ":" + Base64(AES数据)）
     * @return 解密后的明文
     */
    public static String hybridDecrypt(String encryptedText) {
        if (encryptedText == null) return null;
        try {
            // 1. 分割RSA加密的密钥和AES加密的数据
            String[] parts = encryptedText.split(":", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("密文格式错误");
            }

            byte[] encryptedKey = Base64.getDecoder().decode(parts[0]);
            byte[] encryptedData = Base64.getDecoder().decode(parts[1]);

            // 2. 使用RSA解密AES密钥和IV
            PrivateKey privateKey = RSAKeyManager.getPrivateKey();
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] keyAndIV = rsaCipher.doFinal(encryptedKey);

            // 3. 提取AES密钥和IV
            byte[] aesKey = new byte[16];
            byte[] iv = new byte[16];
            System.arraycopy(keyAndIV, 0, aesKey, 0, 16);
            System.arraycopy(keyAndIV, 16, iv, 0, 16);

            // 4. 使用AES解密数据
            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            aesCipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedData = aesCipher.doFinal(encryptedData);

            String result = new String(decryptedData, StandardCharsets.UTF_8);
            System.out.println("[混合解密:RSA解密AES密钥 + AES解密数据] 密文长度: " + encryptedText.length() + ", 原文长度: " + result.length() + "，解密结果：" + result);
            return result;

        } catch (Exception e) {
            System.err.println("[混合解密:RSA解密AES密钥 + AES解密数据] 解密失败: " + e.getMessage());
            throw new RuntimeException("混合解密失败", e);
        }
    }

    /**
     * 获取RSA公钥
     */
    public static PublicKey getRSAPublicKey() {
        return RSAKeyManager.getPublicKey();
    }

    /**
     * 获取RSA私钥
     */
    public static PrivateKey getRSAPrivateKey() {
        return RSAKeyManager.getPrivateKey();
    }


}


/**
 * RSA加密工具类
 * 提供RSA非对称加密相关的功能
 */
class RSAUtil {

    private static final String RSA_ALGORITHM = "RSA";
    private static final int RSA_KEY_SIZE = 2048;

    /**
     * RSA生成密钥对
     *
     * @return KeyPair 包含公钥和私钥的密钥对
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGenerator.initialize(RSA_KEY_SIZE);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("RSA 密钥对生成失败", e);
        }
    }

    /**
     * RSA公钥加密字符串
     *
     * @param text      待加密的数据
     * @param publicKey 公钥
     * @return 加密后的数据，使用Base64编码
     */
    public static String encrypt(String text, PublicKey publicKey) {
        if (text == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("RSA 加密失败", e);
        }
    }

    /**
     * RSA私钥解密字符串
     *
     * @param text       加密后的数据，使用Base64编码
     * @param privateKey 私钥
     * @return 解密后的数据
     */
    public static String decrypt(String text, PrivateKey privateKey) {
        if (text == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedData = Base64.getDecoder().decode(text);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("RSA 解密失败", e);
        }
    }

    /**
     * RSA公钥加密字节数组
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("RSA 加密失败，数据长度不得超过245字节", e);
        }
    }

    /**
     * RSA私钥解密字节数组
     */
    public static byte[] decrypt(byte[] cipherText, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("RSA 解密失败", e);
        }
    }

    /**
     * 将PublicKey转换为Base64编码的字符串
     */
    public static String publicKeyToBase64(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * 将PrivateKey转换为Base64编码的字符串
     */
    public static String privateKeyToBase64(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 从Base64编码的字符串还原PublicKey
     */
    public static PublicKey base64ToPublicKey(String publicKeyStr) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("PublicKey 还原失败", e);
        }
    }

    /**
     * 从Base64编码的字符串还原PrivateKey
     */
    public static PrivateKey base64ToPrivateKey(String privateKeyStr) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("PrivateKey 还原失败", e);
        }
    }
}

/**
 * RSA密钥管理服务
 * 负责密钥的生成、保存、加载和管理
 * 采用延迟加载策略，仅在首次使用时初始化
 */
class RSAKeyManager {

    private static final String KEY_CONFIG_FILE = "rsa-keys.properties";
    private static final String PUBLIC_KEY_PROPERTY = "rsa.public.key";
    private static final String PRIVATE_KEY_PROPERTY = "rsa.private.key";

    private static PublicKey cachedPublicKey = null;
    private static PrivateKey cachedPrivateKey = null;
    private static boolean isInitialized = false;

    /**
     * 获取或初始化密钥对（延迟加载）
     * 仅在首次调用时初始化密钥对，后续调用直接使用缓存
     */
    public static synchronized KeyPair getKeyPair() {
        try {
            if (cachedPublicKey != null && cachedPrivateKey != null) {
                return new KeyPair(cachedPublicKey, cachedPrivateKey);
            }

            Properties props = loadKeyProperties();
            if (props.containsKey(PUBLIC_KEY_PROPERTY) && props.containsKey(PRIVATE_KEY_PROPERTY)) {
                String publicKeyStr = props.getProperty(PUBLIC_KEY_PROPERTY);
                String privateKeyStr = props.getProperty(PRIVATE_KEY_PROPERTY);

                PublicKey publicKey = RSAUtil.base64ToPublicKey(publicKeyStr);
                PrivateKey privateKey = RSAUtil.base64ToPrivateKey(privateKeyStr);

                cachedPublicKey = publicKey;
                cachedPrivateKey = privateKey;
                isInitialized = true;

                System.out.println("[RSA密钥管理] 已从配置文件加载RSA密钥对");
                return new KeyPair(publicKey, privateKey);
            }

            System.out.println("[RSA密钥管理] 未找到现有密钥，正在生成新的RSA密钥对...");
            KeyPair newKeyPair = RSAUtil.generateKeyPair();

            saveKeyPair(newKeyPair);

            cachedPublicKey = newKeyPair.getPublic();
            cachedPrivateKey = newKeyPair.getPrivate();
            isInitialized = true;

            System.out.println("[RSA密钥管理] 新的RSA密钥对已生成并保存到: " + KEY_CONFIG_FILE);
            return newKeyPair;

        } catch (Exception e) {
            System.err.println("[RSA密钥管理] 获取密钥对失败: " + e.getMessage());
            throw new RuntimeException("获取RSA密钥对失败", e);
        }
    }

    /**
     * 获取公钥（触发延迟加载）
     */
    public static PublicKey getPublicKey() {
        return getKeyPair().getPublic();
    }

    /**
     * 获取私钥（触发延迟加载）
     */
    public static PrivateKey getPrivateKey() {
        return getKeyPair().getPrivate();
    }

    /**
     * 检查是否已初始化
     */
    public static boolean isInitialized() {
        return isInitialized;
    }

    /**
     * 保存密钥对到配置文件
     */
    private static void saveKeyPair(KeyPair keyPair) throws IOException {
        Properties props = new Properties();

        String publicKeyStr = RSAUtil.publicKeyToBase64(keyPair.getPublic());
        String privateKeyStr = RSAUtil.privateKeyToBase64(keyPair.getPrivate());

        props.setProperty(PUBLIC_KEY_PROPERTY, publicKeyStr);
        props.setProperty(PRIVATE_KEY_PROPERTY, privateKeyStr);

        try (FileOutputStream fos = new FileOutputStream(KEY_CONFIG_FILE)) {
            props.store(fos, "RSA Keys Configuration - Generated on " + new java.util.Date());
        }
    }

    /**
     * 从配置文件加载密钥属性
     */
    private static Properties loadKeyProperties() throws IOException {
        Properties props = new Properties();

        File configFile = new File(KEY_CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
            }
        }

        return props;
    }

    /**
     * 清除缓存的密钥
     */
    public static void clearKeyCache() {
        cachedPublicKey = null;
        cachedPrivateKey = null;
        isInitialized = false;
        System.out.println("[RSA密钥管理] 密钥缓存已清除");
    }

    /**
     * 重新生成密钥对（会覆盖原有密钥）
     */
    public static KeyPair regenerateKeyPair() {
        System.out.println("[RSA密钥管理] 警告：正在重新生成RSA密钥对，旧密钥将被覆盖！");

        clearKeyCache();

        File configFile = new File(KEY_CONFIG_FILE);
        if (configFile.exists()) {
            configFile.delete();
            System.out.println("[RSA密钥管理] 已删除旧的密钥配置文件");
        }

        return getKeyPair();
    }

    /**
     * 检查密钥文件是否存在
     */
    public static boolean keyFileExists() {
        return new File(KEY_CONFIG_FILE).exists();
    }

    /**
     * 获取密钥文件信息
     */
    public static String getKeyFileInfo() {
        File configFile = new File(KEY_CONFIG_FILE);
        if (configFile.exists()) {
            return String.format("密钥文件: %s (大小: %d bytes, 修改时间: %s)",
                    KEY_CONFIG_FILE,
                    configFile.length(),
                    new java.util.Date(configFile.lastModified()));
        } else {
            return "密钥文件不存在";
        }
    }
}
