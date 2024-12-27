package com.example.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Utils {

    /**
     * 计算给定字符串的 SHA-256 哈希值
     *
     * @param originalString 要进行哈希的原始字符串
     * @return 字符串的 SHA-256 哈希值
     */
    public  String SHE256hash(String originalString) {
        try {
            // 创建 MessageDigest 对象，指定使用 SHA-256 算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 将字符串转换为字节数组
            byte[] encodedhash = digest.digest(originalString.getBytes());

            // 将字节数组转换为十六进制字符串
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将字节数组转换为十六进制字符串
    private  String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
