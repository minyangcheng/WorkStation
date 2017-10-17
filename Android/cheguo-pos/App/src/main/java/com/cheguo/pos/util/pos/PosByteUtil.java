package com.cheguo.pos.util.pos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by lenovo on 2016/10/10.
 */

public class PosByteUtil {
    //
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

    //        private static final Logger logger = LoggerFactory.getLogger(ByteUtil.class);
    private static String hexStr = "0123456789ABCDEF";
    public static final char[] BToA = "0123456789abcdef".toCharArray();
    public static final String nLine = "----------------------------------------------------------------------------";
    private static final int DECIMAL = 2;

    public PosByteUtil() {
    }

    public static byte[] Int2ByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];

        for (int i = iArrayLen; i < 4 && i > 0; --i) {
            bLocalArr[i - 1] = (byte) (iSource >> 8 * (iArrayLen - i) & 255);
        }

        return bLocalArr;
    }

    public static String trace(byte[] inBytes) {
        int j = 0;
        byte[] temp = new byte[76];
        bytesSet(temp, ' ');
        StringBuffer strc = new StringBuffer("");
        strc.append("----------------------------------------------------------------------------\n");

        for (int i = 0; i < inBytes.length; ++i) {
            if (j == 0) {
                System.arraycopy(String.format("%03d: ", new Object[]{Integer.valueOf(i)}).getBytes(), 0, temp, 0, 5);
                System.arraycopy(String.format(":%03d", new Object[]{Integer.valueOf(i + 15)}).getBytes(), 0, temp, 72, 4);
            }

            System.arraycopy(String.format("%02X ", new Object[]{Byte.valueOf(inBytes[i])}).getBytes(), 0, temp, j * 3 + 5 + (j > 7 ? 1 : 0), 3);
            if (inBytes[i] == 0) {
                temp[j + 55 + (j > 7 ? 1 : 0)] = 46;
            } else {
                temp[j + 55 + (j > 7 ? 1 : 0)] = inBytes[i];
            }

            ++j;
            if (j == 16) {
                strc.append(new String(temp)).append("\n");
                bytesSet(temp, ' ');
                j = 0;
            }
        }

        if (j != 0) {
            strc.append(new String(temp)).append("\n");
            bytesSet(temp, ' ');
        }

        strc.append("----------------------------------------------------------------------------\n");
        System.out.println(strc.toString());
        return strc.toString();
    }

    private static void bytesSet(byte[] inBytes, char fill) {
        if (inBytes.length != 0) {
            for (int i = 0; i < inBytes.length; ++i) {
                inBytes[i] = (byte) fill;
            }

        }
    }

    public static byte[] byteAndByte(byte[] begin, byte[] second) {
        if (begin != null && begin.length != 0) {
            if (second != null && second.length != 0) {
                byte[] newTotal = new byte[begin.length + second.length];

                int i;
                for (i = 0; i < begin.length; ++i) {
                    newTotal[i] = begin[i];
                }

                for (i = begin.length; i < second.length + begin.length; ++i) {
                    newTotal[i] = second[i - begin.length];
                }

                return newTotal;
            } else {
                return begin;
            }
        } else {
            return second != null && second.length != 0 ? second : null;
        }
    }

    public static byte[] getsubByte(byte[] total, int begin, int length) {
        if (length <= 0) {
            return new byte[0];
        } else {
            byte[] newTotal = new byte[length];

            for (int i = begin; i < length + begin; ++i) {
                newTotal[i - begin] = total[i];
            }

            return newTotal;
        }
    }

    public static String fillString(String string, char filler, int totalLength, boolean atEnd) {
        byte[] tempbyte = string.getBytes();
        int currentLength = tempbyte.length;
        int delta = totalLength - currentLength;

        for (int i = 0; i < delta; ++i) {
            if (atEnd) {
                string = string + filler;
            } else {
                string = filler + string;
            }
        }

        return string;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for (int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for (int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString().toUpperCase();
        } else {
            return null;
        }
    }

    public static String bytesToString(byte[] src, int begin, int length) {
        String str1 = null;
        StringBuilder sb = new StringBuilder("");
        if (begin == 0 && length == 0) {
            byte[] var8 = src;
            int var7 = src.length;

            for (int var10 = 0; var10 < var7; ++var10) {
                byte var9 = var8[var10];
                sb.append(String.valueOf(var9));
            }
        } else {
            for (int i = begin; i < begin + length; ++i) {
                byte element = src[i];
                sb.append(String.valueOf(element));
            }
        }

        str1 = sb.toString();
        System.out.println(str1);
        return str1;
    }

    public static String BinaryToHexString(byte[] bytes) {
        String result = "";
        String hex = "";

        for (int i = 0; i < bytes.length; ++i) {
            hex = String.valueOf(hexStr.charAt((bytes[i] & 240) >> 4));
            hex = hex + String.valueOf(hexStr.charAt(bytes[i] & 15));
            result = result + hex;
        }

        return result.toUpperCase();
    }

    public static String binaryToDecimal(String binary) {
        StringBuffer buf = new StringBuffer();
        String[] strBinary = binary.split(" ");
        String[] var6 = strBinary;
        int var5 = strBinary.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            String str = var6[var4];
            StringBuffer strBuf = new StringBuffer(str);
            char[] element = strBuf.reverse().toString().toCharArray();
            int digit = 0;
            int result = 0;
            char[] var14 = element;
            int var13 = element.length;

            for (int var12 = 0; var12 < var13; ++var12) {
                char temp = var14[var12];
                int intNumber = Integer.parseInt(Character.toString(temp));
                intNumber *= (int) Math.pow(2.0D, (double) digit);
                result += intNumber;
                ++digit;
            }

            buf.append(result);
            buf.append(" ");
        }

        return buf.toString();
    }

    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];

        for (int e1 = 0; e1 < baKeyword.length; ++e1) {
            try {
                baKeyword[e1] = (byte) (255 & Integer.parseInt(s.substring(e1 * 2, e1 * 2 + 2), 16));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        try {
            s = new String(baKeyword, "utf-8");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return s;
    }

    public static String toStringHex(String s, String charset) {
        byte[] baKeyword = new byte[s.length() / 2];

        for (int e1 = 0; e1 < baKeyword.length; ++e1) {
            try {
                baKeyword[e1] = (byte) (255 & Integer.parseInt(s.substring(e1 * 2, e1 * 2 + 2), 16));
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        try {
            s = new String(baKeyword, charset);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return s;
    }

    public static String cbcd2string(byte[] bytes, boolean format, int length) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int val = 0; val < bytes.length; ++val) {
            temp.append((byte) ((bytes[val] & 240) >>> 4));
            temp.append((byte) (bytes[val] & 15));
        }

        String var5 = temp.toString();
        return format ? (var5.length() > length ? (var5.substring(0, 1).equalsIgnoreCase("0") ? var5.substring(1) : var5) : var5) : (var5.length() > length ? (var5.substring(var5.length() - 1, var5.length()).equalsIgnoreCase("0") ? var5.substring(0, var5.length() - 1) : var5) : var5);
    }

    public static byte[] str2cbcd(String asc, boolean format) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            if (format) {
                asc = "0" + asc;
                len = asc.length();
            } else {
                asc = asc + "0";
                len = asc.length();
            }
        }

        byte[] abt = new byte[len];
        if (len >= 2) {
            len /= 2;
        }

        byte[] bbt = new byte[len];
        abt = asc.getBytes();

        for (int p = 0; p < asc.length() / 2; ++p) {
            int j;
            if (abt[2 * p] >= 48 && abt[2 * p] <= 57) {
                j = abt[2 * p] - 48;
            } else if (abt[2 * p] >= 97 && abt[2 * p] <= 122) {
                j = abt[2 * p] - 97 + 10;
            } else {
                j = abt[2 * p] - 65 + 10;
            }

            int k;
            if (abt[2 * p + 1] >= 48 && abt[2 * p + 1] <= 57) {
                k = abt[2 * p + 1] - 48;
            } else if (abt[2 * p + 1] >= 97 && abt[2 * p + 1] <= 122) {
                k = abt[2 * p + 1] - 97 + 10;
            } else {
                k = abt[2 * p + 1] - 65 + 10;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }

        return bbt;
    }

    public static byte[] str2Bcd(String s, boolean format) {
        if (s.length() % 2 != 0) {
            if (format) {
                s = "0" + s;
            } else {
                s = s + "0";
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        char[] cs = s.toCharArray();

        for (int i = 0; i < cs.length; i += 2) {
            int high = cs[i] - 48;
            if (high > 9) {
                high -= 7;
            }

            int low = cs[i + 1] - 48;
            if (low > 9) {
                low -= 7;
            }

            baos.write(high << 4 | low);
        }

        return baos.toByteArray();
    }

    public static String bcd2Str(byte[] b, boolean format, int length) {
        StringBuffer sb = new StringBuffer();

        for (int val = 0; val < b.length; ++val) {
            int h = ((b[val] & 255) >> 4) + 48;
            if (h > 57) {
                h += 7;
            }

            sb.append((char) h);
            int l = (b[val] & 15) + 48;
            if (l > 57) {
                l += 7;
            }

            sb.append((char) l);
        }

        String var7 = sb.toString().toUpperCase();
        if (format) {
            if (var7.length() > length) {
                return var7.substring(0, 1).equalsIgnoreCase("0") ? var7.substring(1) : var7;
            } else {
                return var7;
            }
        } else if (var7.length() > length) {
            return var7.substring(var7.length() - 1, var7.length()).equalsIgnoreCase("0") ? var7.substring(0, var7.length() - 1) : var7;
        } else {
            return var7;
        }
    }

    public static String BCD2ASC(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            int h = (bytes[i] & 240) >>> 4;
            int l = bytes[i] & 15;
            temp.append(BToA[h]).append(BToA[l]);
        }

        return temp.toString().toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(hexStringToBytes("DC4F8DA27B17CF17BC17306DF2FD41C7C5CA7C2F8325677280B03B0A656BABD6FF352A8A6064").length);
        System.out.println(bytesToHexString(hexStringToBytes("005B")));
        System.out.println(String.format("%04d", new Object[]{Integer.valueOf(256)}));
        System.out.println(fillString("256", '0', 4, false));
        System.out.println(Integer.toHexString(90));
        System.out.println(hexStringToBytes("D7C7CA5A868F1A5C71BF9D54DF28C5D191DFB7FB442EE6154CC00EC70262FCCEB63051BF2327"));
        System.out.println(hexStringToBytes("FF").length);
        System.out.println(bytesToHexString(hexStringToBytes("0058")));
        System.out.println("0058 = " + Integer.parseInt(bytesToHexString(hexStringToBytes("0058")), 16));
        System.out.println(Integer.parseInt(toStringHex(BinaryToHexString("005a".getBytes())), 16));
        byte len = 4;
        if (len % 2 != 0) {
            System.out.println(len / 2 + 1);
        } else {
            System.out.println(len / 2);
        }

        int[] fun = new int[]{0, 1, 2, 3, 4, 5, 6};
        System.arraycopy(fun, 0, fun, 3, 3);
        int[] str = fun;
        int t = fun.length;

        for (int d = 0; d < t; ++d) {
            int s = str[d];
            System.out.print(s);
        }

        System.out.print("");
        System.out.println("088".getBytes().length);
        System.out.println("test = " + "00000000010000".length());
        System.out.println(" str2Bcd(011) length " + str2Bcd("011", true).length);
        System.out.println(" str2Bcd(011) " + bcd2Str(str2Bcd("011", true), true, 3));
        System.out.println(" str2Bcd(00000001901) " + bcd2Str(str2Bcd("00000001901", false), false, 11));
        System.out.println(" str2Bcd(09=0) " + bcd2Str(str2Bcd("09=0", false), false, 4));
        System.out.println(" str2Bcd(09=0) " + BinaryToHexString(str2Bcd("09=0", false)));
        System.out.println("bytesToString = " + toStringHex(BinaryToHexString("0003".getBytes())));
        String var7 = "0123";
        byte[] var8 = var7.getBytes();
        String var9 = new String(var8);
        System.out.println(var9);
        String var10 = "88234234";
        System.out.println(toHexString(var10));
    }

    public static String toHexString(String s) {
        String str = " ";

        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }

        return str;
    }

    public static void d() {
        System.out.println(Integer.toHexString(200));
        Integer.parseInt("8C", 16);
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //把图片资料转为十六进制字符串
    public static String getHexStr(Context context, int imgId) {
        Drawable drawable = context.getResources().getDrawable(imgId);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        byte[] bs = Bitmap2Bytes(bitmap);
        String hexStr = bytesToHexString(bs);
        return hexStr;
    }
}
