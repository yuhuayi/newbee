package app.newbee.lib.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesCoder {
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};


    public static String decode(String key, String message) {
        if (message == null)
            return null;
        try {
//            byte[] bytesrc = Base64Utils.decode(new String(hexStr2ByteArr(message)));
            byte[] bytesrc = Base64Utils.decode(new String(message.getBytes()));

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }


    public static String encode(String key, String message) { //
        if (message == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptbyte = cipher.doFinal(message.getBytes());
            return new String(Base64Utils.encode(encryptbyte).getBytes());
//            return byteArr2HexStr(Base64Utils.encode(encryptbyte).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }


    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }


    public static void main(String[] args) {
//        try {
//            System.out.println("????");
//            System.out.println(encode("goeb2c2b", "????"));
//            System.out.println(decode("goeb2c2b", encode("goeb2c2b", "??")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
        try {

//            System.out.println(encode("goeb2c2b", new String("1234!@#$%^&中文字符".getBytes("UTF-8")))); // wApG3a0yfhC23t+Kcmw9uw==
//            System.out.println(encode("goeb2c2b", new String("我我我我".getBytes("GBK")))); // XZR6H56lZh3ot1Bb2LKDRa3xLYn1jInQYflwk6d30UI=
//            System.out.println(encode("goeb2c2b", new String("我我我我".getBytes("GB2312")))); // XZR6H56lZh3ot1Bb2LKDRa3xLYn1jInQYflwk6d30UI=
//            System.out.println(encode("goeb2c2b", new String("我我我我".getBytes("ISO-8859-1")))); // GP0E6rBonJc=
//            System.out.println(encode("goeb2c2b", new String("我我我我".getBytes("UTF-16")))); // x+N3ECQN5YY9hpEQq2Ssgw==


//            System.out.println("--------------------");
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("GBK"), "UTF-8")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("GBK"), "GBK")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("GBK"), "GB2312")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("GBK"), "ISO-8859-1")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("GBK"), "UTF-16")); //
//            System.out.println("--------------------");
//
//            System.out.println("--------------------");
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("UTF-8"), "UTF-8")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("UTF-8"), "GBK")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("UTF-8"), "GB2312")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("UTF-8"), "ISO-8859-1")); //
//            System.out.println(new String(encode("goeb2c2b", "我我我我").getBytes("UTF-8"), "UTF-16")); //
//            System.out.println("--------------------");
//
//            String ss = encode("goeb2c2b", new String("我我我我".getBytes("UTF-8")));
//            String ss = "FvFGF5ZWWPMSk4w943frAw==";
//            System.out.println(decode("goeb2c2b", new String(ss.getBytes("UTF-8"))));
//            System.out.println(decode("goeb2c2b", new String(ss.getBytes("GBK"))));
//
//            System.out.println(decode("goeb2c2b", new String(ss.getBytes("GBK"), "GBK")));
//            System.out.println(decode("goeb2c2b", new String(ss.getBytes("GBK"), "UTF-8")));
//            System.out.println(decode("goeb2c2b", new String(ss.getBytes("UTF-8"), "GBK")));
//            System.out.println(decode("goeb2c2b", new String(ss.getBytes("UTF-8"), "UTF-8")));

//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("UTF-8"))).getBytes("UTF-8"), "UTF-8"));
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("GBK"))).getBytes("GBK"), "GBK"));
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("GBK"))).getBytes("UTF-8"), "UTF-8"));
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("UTF-8"))).getBytes("GBK"), "GBK"));
//
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("UTF-8"))).getBytes("GBK"), "UTF-8"));
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("UTF-8"))).getBytes("UTF-8"), "GBK"));
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("GBK"))).getBytes("UTF-8"), "GBK"));
//            System.out.println(new String(decode("goeb2c2b", new String(ss.getBytes("GBK"))).getBytes("GBK"), "UTF-8"));
            System.out.println(decode("goeb2c2b", "lveYQONzwCcTPdVQDbHAK1TicgLwdC12G2wNKNzztBOROcr0yiM8c2ugeRimzaP0vuDnrYzU7PjdbZC38kety3/pNtx8jjv0Pq0MhxUNEugp/YWPKHEHda/JNufoSlv3laRaIYWfg7PoRP5gOCjwzLyR45U7Mvau9GVTY+IsBw+1BhBQEpkb5xkGQbTFneo1auAYgzV5WyUNddK7oYwUvx3WyPfOCFFunDgvXXBSBvHW6NlrC0oleGMp/9CnkvVZaYyJzqupcHw0U1IwJUSuwewGEXtzrmtwuxPqN4MmHxoQ61LsH8K/0vsiZP2nLlmbM031n87aBpIm2k1vl/vg0Z64xyqw1um0kzyBs6/MH0QOejMOsIxddg==\n"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}