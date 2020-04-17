package shuangjia.shuangjia.util;
import shuangjia.shuangjia.entities.Data;
import shuangjia.shuangjia.entities.DeviceProperty;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 数字转字节数组工具类
 */
public class BitConverter {

	/** 16进制中的字符�? */
	private static final String HEX_CHAR = "0123456789ABCDEF";
	
	/** 16进制中的字符集对应的字节数组 */
	private static final byte[] HEX_STRING_BYTE = HEX_CHAR.getBytes();

    /**
     * 以字节数组的形式返回指定的布尔�??
     * @param data �?个布尔�??
     * @return 长度�? 1 的字节数�?
     */
    public static byte[] getBytes(boolean data) {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (data ? 1 : 0);
        return bytes;
    }
    
    /**
     * 以字节数组的形式返回指定�? 16 位有符号整数�?
     * @param data 要转换的数字
     * @return 长度�? 2 的字节数�?
     */
    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data & 0xff00) >> 8);
        } else {
            bytes[1] = (byte) (data & 0xff);
            bytes[0] = (byte) ((data & 0xff00) >> 8);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定�? Unicode 字符�?
     * @param data 要转换的字符
     * @return 长度�? 2 的字节数�?
     */
    public static byte[] getBytes(char data) {
        byte[] bytes = new byte[2];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data);
            bytes[1] = (byte) (data >> 8);
        } else {
            bytes[1] = (byte) (data);
            bytes[0] = (byte) (data >> 8);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定�? 32 位有符号整数�?
     * @param data 要转换的数字
     * @return 长度�? 4 的字节数�?
     */
    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data & 0xff00) >> 8);
            bytes[2] = (byte) ((data & 0xff0000) >> 16);
            bytes[3] = (byte) ((data & 0xff000000) >> 24);
        } else {
            bytes[3] = (byte) (data & 0xff);
            bytes[2] = (byte) ((data & 0xff00) >> 8);
            bytes[1] = (byte) ((data & 0xff0000) >> 16);
            bytes[0] = (byte) ((data & 0xff000000) >> 24);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定�? 64 位有符号整数�?
     * @param data 要转换的数字
     * @return 长度�? 8 的字节数�?
     */
    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data >> 8) & 0xff);
            bytes[2] = (byte) ((data >> 16) & 0xff);
            bytes[3] = (byte) ((data >> 24) & 0xff);
            bytes[4] = (byte) ((data >> 32) & 0xff);
            bytes[5] = (byte) ((data >> 40) & 0xff);
            bytes[6] = (byte) ((data >> 48) & 0xff);
            bytes[7] = (byte) ((data >> 56) & 0xff);
        } else {
            bytes[7] = (byte) (data & 0xff);
            bytes[6] = (byte) ((data >> 8) & 0xff);
            bytes[5] = (byte) ((data >> 16) & 0xff);
            bytes[4] = (byte) ((data >> 24) & 0xff);
            bytes[3] = (byte) ((data >> 32) & 0xff);
            bytes[2] = (byte) ((data >> 40) & 0xff);
            bytes[1] = (byte) ((data >> 48) & 0xff);
            bytes[0] = (byte) ((data >> 56) & 0xff);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定的单精度浮点�?
     * @param data 要转换的数字
     * @return 长度�? 4 的字节数�?
     */
    public static byte[] getBytes(float data) {
        return getBytes(Float.floatToIntBits(data));
    }

    /**
     * 以字节数组的形式返回指定的双精度浮点�?
     * @param data 要转换的数字
     * @return 长度�? 8 的字节数�?
     */
    public static byte[] getBytes(double data) {
        return getBytes(Double.doubleToLongBits(data));
    }
    
    /**
     * 将指定字符串中的�?有字符编码为�?个字节序�?
     * @param data 包含要编码的字符的字符串
     * @return �?个字节数组，包含对指定的字符集进行编码的结果
     */
    public static byte[] getBytes(String data) {
        return data.getBytes(Charset.forName("UTF-8"));
    }
    
    /**
     * 将指定字符串中的�?有字符编码为�?个字节序�?
     * @param data 包含要编码的字符的字符串
     * @param charsetName 字符集编�?
     * @return �?个字节数组，包含对指定的字符集进行编码的结果
     */
    public static byte[] getBytes(String data, String charsetName) {
        return data.getBytes(Charset.forName(charsetName));
    }
    
    /**
     * 返回由字节数组转换来的布尔�??
     * @param bytes 字节数组
     * @return 布尔�?
     */
    public static boolean toBoolean(byte[] bytes) {
        return bytes[0] == 0 ? false : true;
    }
    
    /**
     * 返回由字节数组中的指定的�?个字节转换来的布尔�??
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 布尔�?
     */
    public static boolean toBoolean(byte[] bytes, int startIndex) {
        return toBoolean(copyFrom(bytes, startIndex, 1));
    }
    
    /**
     * 返回由字节数组转换来�? 16 位有符号整数
     * @param bytes 字节数组
     * @return 由两个字节构成的 16 位有符号整数
     */
    public static short toShort(byte[] bytes) {
        if (isLittleEndian()) {
            return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
        } else {
            return (short) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
        }
    }
    
    /**
     * 返回由字节数组中的指定的两个字节转换来的 16 位有符号整数
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 由两个字节构成的 16 位有符号整数
     */
    public static short toShort(byte[] bytes, int startIndex) {
        return toShort(copyFrom(bytes, startIndex, 2));
    }

    /**
     * 返回由字节数组转换来�? Unicode 字符
     * @param bytes 字节数组
     * @return 由两个字节构成的字符
     */
    public static char toChar(byte[] bytes) {
        if (isLittleEndian()) {
            return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
        } else {
            return (char) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
        }
    }
    
    /**
     * 返回由字节数组中的指定的两个字节转换来的 Unicode 字符
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 由两个字节构成的字符
     */
    public static char toChar(byte[] bytes, int startIndex) {
        return toChar(copyFrom(bytes, startIndex, 2));
    }

    /**
     * 返回由字节数组转换来�? 32 位有符号整数
     * @param bytes 字节数组
     * @return 由四个字节构成的 32 位有符号整数
     */
    public static int toInt(byte[] bytes) {
        if (isLittleEndian()) {
            return (0xff & bytes[0]) 
                    | (0xff00 & (bytes[1] << 8)) 
                    | (0xff0000 & (bytes[2] << 16))
                    | (0xff000000 & (bytes[3] << 24));
        } else {
            return (0xff & bytes[3]) 
                    | (0xff00 & (bytes[2] << 8)) 
                    | (0xff0000 & (bytes[1] << 16))
                    | (0xff000000 & (bytes[0] << 24));
        }
    }
    
    /**
     * 返回由字节数组中的指定的四个字节转换来的 32 位有符号整数
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 由四个字节构成的 32 位有符号整数
     */
    public static int toInt(byte[] bytes, int startIndex) {
        return toInt(copyFrom(bytes, startIndex, 4));
    }

    /**
     * 返回由字节数组转换来�? 64 位有符号整数
     * @param bytes 字节数组
     * @return 由八个字节构成的 64 位有符号整数
     */
    public static long toLong(byte[] bytes) {
        if (isLittleEndian()) {
            return (0xffL & (long) bytes[0]) 
                    | (0xff00L & ((long) bytes[1] << 8)) 
                    | (0xff0000L & ((long) bytes[2] << 16))
                    | (0xff000000L & ((long) bytes[3] << 24)) 
                    | (0xff00000000L & ((long) bytes[4] << 32))
                    | (0xff0000000000L & ((long) bytes[5] << 40)) 
                    | (0xff000000000000L & ((long) bytes[6] << 48))
                    | (0xff00000000000000L & ((long) bytes[7] << 56));
        } else {
            return (0xffL & (long) bytes[7]) 
                    | (0xff00L & ((long) bytes[6] << 8)) 
                    | (0xff0000L & ((long) bytes[5] << 16))
                    | (0xff000000L & ((long) bytes[4] << 24)) 
                    | (0xff00000000L & ((long) bytes[3] << 32))
                    | (0xff0000000000L & ((long) bytes[2] << 40)) 
                    | (0xff000000000000L & ((long) bytes[1] << 48))
                    | (0xff00000000000000L & ((long) bytes[0] << 56));
        }
    }
    
    /**
     * 返回由字节数组中的指定的八个字节转换来的 64 位有符号整数
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 由八个字节构成的 64 位有符号整数
     */
    public static long toLong(byte[] bytes, int startIndex) {
        return toLong(copyFrom(bytes, startIndex, 8));
    }

    /**
     * 返回由字节数组转换来的单精度浮点�?
     * @param bytes 字节数组
     * @return 由四个字节构成的单精度浮点数
     */
    public static float toFloat(byte[] bytes) {
        return Float.intBitsToFloat(toInt(bytes));
    }
    
    /**
     * 返回由字节数组中的指定的四个字节转换来的单精度浮点数
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 由四个字节构成的单精度浮点数
     */
    public static float toFloat(byte[] bytes, int startIndex) {
        return Float.intBitsToFloat(toInt(copyFrom(bytes, startIndex, 4)));
    }

    /**
     * 返回由字节数组转换来的双精度浮点�?
     * @param bytes 字节数组
     * @return 由八个字节构成的双精度浮点数
     */
    public static double toDouble(byte[] bytes) {
        return Double.longBitsToDouble(toLong(bytes));
    }
    
    /**
     * 返回由字节数组中的指定的八个字节转换来的双精度浮点数
     * @param bytes 字节数组
     * @param startIndex 起始下标
     * @return 由八个字节构成的双精度浮点数
     */
    public static double toDouble(byte[] bytes, int startIndex) {
        return Double.longBitsToDouble(toLong(copyFrom(bytes, startIndex, 8)));
    }
    
    /**
     * 返回由字节数组转换来的字符串
     * @param bytes 字节数组
     * @return 字符�?
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }
    
    /**
     * 返回由字节数组转换来的字符串
     * @param bytes 字节数组
     * @param charsetName 字符集编�?
     * @return 字符�?
     */
    public static String toString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }
    
    /**
     * 以字符串表示形式返回字节数组的内�?
     * @param bytes 字节数组
     * @return 字符串形式的 <tt>bytes</tt>
     */
    public static String toHexString(byte[] bytes) {
        if (bytes == null)
            return "null";
        int iMax = bytes.length - 1;
        if (iMax == -1)
            return "[]";
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.format("%02x", bytes[i] & 0xFF));
            if (i == iMax)
                return b.toString();
            
        }
    }
    

/**

 * byte数组转int类型的对�?

 * @param bytes

 * @return  转换高位在前低位在后的byte

 */
    public static int ByteTOIntHighLow(byte[]bytes) {  
    	return (bytes[0]&0xff)<<24      
    			| (bytes[1]&0xff)<<16      
    			| (bytes[2]&0xff)<<8      
    			| (bytes[3]&0xff);
    }

    /**

     * byte数组转int类型的对�?

     * @param bytes

     * @return  转换低位在前高位在后的byte

     */
    public static int Byte2IntLowHigh(byte[]bytes) {  
    	return (bytes[2]&0xff)<<24     
    			| (bytes[3]&0xff)<<16      
    			| (bytes[0]&0xff)<<8     
    			| (bytes[1]&0xff);   
    	}

    
    public static float byteToFloat(byte[] byteArray){

        

    	int res=Byte2IntLowHigh(byteArray);
    	float m= Float.intBitsToFloat(res);

    	return m;

    	}
    
    /**
     * 数组拷贝�?
     * @param src 字节数组�?
     * @param off 起始下标�?
     * @param len 拷贝长度�?
     * @return 指定长度的字节数组�??
     */
    private static byte[] copyFrom(byte[] src, int off, int len) {
        // return Arrays.copyOfRange(src, off, off + len);
        byte[] bits = new byte[len];
        for (int i = off, j = 0; i < src.length && j < len; i++, j++) {
            bits[j] = src[i];
        }
        return bits;
    }
    
    /**
     * 判断 CPU Endian 是否�? Little
     * @return 判断结果
     */
    private static boolean isLittleEndian() {
        return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    }
    
    
    /**     * 16进制表示的字符串转换为字节数�?     *    
     * * @param hexString 16进制表示的字符串    
     *  * @return byte[] 字节数组     */   
    public static byte[] hexStringToByteArray(String hexString) {       
    	hexString = hexString.replaceAll(" ", "");        
    	int len = hexString.length();       
    	byte[] bytes = new byte[len / 2];       
    	for (int i = 0; i < len; i += 2) {            
    		// 两位�?组，表示�?个字�?,把这样表示的16进制字符串，还原成一个字�?           
    		bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
    				.digit(hexString.charAt(i+1), 16));       
    		}        
    	return bytes;   
    		
    }

    //字节转二进制字符串
    public static StringBuffer conver2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2));
        }
        int num=result.length();
        if(num<8) {
            for(int i=8;i>num;i--) {
                result.insert(0,"0");
            }
        }
        return result;
    }
    

    
    public static void main(String[] args) {
        byte[] a=new byte[]{0X00,0X00,0X01,0X00,0X00,0X04,0X01,0X0,0X08,(byte) 0X08};
        byte[] b=new byte[]{(byte) 0X9F,0X65,0X42,0X4A, (byte) 0XAE, (byte) 0XB3,0X41, (byte) 0XF1};
        //StringBuffer cover2=conver2HexStr(new byte[] {a[1]});
        int i=0;
        int j=4;
        float q=BitConverter.toFloat(new byte[] {b[i+1],b[i+0],b[i+3],b[i+2]},0);
        float d=BitConverter.toFloat(new byte[] {b[j+1],b[j+0],b[j+3],b[j+2]}, 0);
        //System.out.println(q);
        //System.out.println(d);
    /*	byte[] a=new byte[]{(byte) 0X8f,(byte)0Xf8,(byte) 0X8e,(byte) 0X3e};
    	String st="01000000000701030471D26ABB";
    	byte[] c=hexStringToByteArray(st);
    	System.out.println("a:"+toFloat(a));
    	System.out.println("ststs:"+Arrays.toString(c));*/

    	//
    	//System.out.println("a:"+toFloat(a));
        int v=108;
        for(int c=0;c<8;c++){
            if((c+109)==113 || c+109==116){
                System.out.println("不操作");
            }else {
                System.out.println(v+1);
            }
        }
    	
	}
}

