/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Huabin Hou
 */
public class BaseTypeConvers {
//整数到字节数组的转换

    public static byte[] intToByte(int intValue) {

        byte[] result = new byte[4];

        result[0] = (byte) ((intValue & 0xFF000000) >> 24);
        result[1] = (byte) ((intValue & 0x00FF0000) >> 16);

        result[2] = (byte) ((intValue & 0x0000FF00) >> 8);
        result[3] = (byte) ((intValue & 0x000000FF));

        return result;
    }

    //字节数组到整数的转换
    public static int byteToInt(byte[] byteVal) {
       
       int result = byteVal[3];
       result |= ((int) byteVal[2] << 8);
       result |= ((int) byteVal[1] << 16);
       result |= ((int) byteVal[0] << 24);
       return result;
    }

    //字符到字节转换
    public static byte[] charToByte(char ch) {
        int temp = (int) ch;
        byte[] b = new byte[2];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue();      //将最高位保存在最低位
            temp = temp >> 8;       //向右移8位
        }
        return b;
    }

    //字节到字符转换
    public static char byteToChar(byte[] b) {
        char s = 0;
        s = (char) b[0];

        s = (char) (s << 8);
        // //System.out.println((int)s);
        s = (char) (s + b[1]);
        char ch = (char) s;
        return ch;
    }

    public static char byteToChar(byte b0, byte b1) {
        char s = 0;
        s = (char) b0;

        s = (char) (s << 8);
        ////System.out.println((int)s);
        s = (char) (s + b1);
        char ch = (char) s;
        return ch;
    }

    //浮点到字节转换
    public static byte[] doubleToByte(double d) {
        byte[] b = new byte[8];
        long l = Double.doubleToLongBits(d);
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(l).byteValue();
            l = l >> 8;


        }
        return b;
    }

      //浮点到字节转换
  /*  public static byte[] longToByte(long l) {
        byte[] b = new byte[8];
       // long l = Double.doubleToLongBits(d);
        for (int i = b.length-1; i >= 0; i--) {
            b[i] = new Long(l).byteValue();
            l = l >> 8;
        }
        return b;
    }*/

     public static byte[] longToByte( long x) {
         byte[] bb= new byte[8];
        bb[0] = (byte) (x >> 56);
        bb[1] = (byte) (x >> 48);
        bb[2] = (byte) (x >> 40);
        bb[3] = (byte) (x >> 32);
        bb[4] = (byte) (x >> 24);
        bb[5] = (byte) (x >> 16);
        bb[6] = (byte) (x >> 8);
        bb[ 7] = (byte) (x >> 0);
        return bb;
    }



     public static long byteToLong(byte[] b) {
        long l=(b[7]& 0xff);
     //   l &= 0xff;
        l |= (((long) b[6]& 0xff )<< 8);
     //   l &= 0xffff;
        l |= (((long) b[5]& 0xff) << 16);
    //    l &= 0xffffff;
        l |= (((long) b[4]& 0xff )<< 24);
     //   l &= 0xffffffffl;
        l |= (((long) b[3]& 0xff )<< 32);
      //  l &= 0xffffffffffl;

        l |= (((long) b[2]& 0xff) << 40);
       // l &= 0xffffffffffffl;
        l |= (((long) b[1]& 0xff )<< 48);


        l |= (((long) b[0]& 0xff )<< 56);
        return l;
    }


     public static long byteToLong(byte[] b,int start) {
        long l=b[start+0];


     //   l &= 0xff;
        l |= ((long) b[start+1] << 8);
     //   l &= 0xffff;
        l |= ((long) b[start+2] << 16);
    //    l &= 0xffffff;
        l |= ((long) b[start+3] << 24);
     //   l &= 0xffffffffl;
        l |= ((long) b[start+4] << 32);
      //  l &= 0xffffffffffl;

        l |= ((long) b[start+5] << 40);
       // l &= 0xffffffffffffl;
        l |= ((long) b[start+6] << 48);


        l |= ((long) b[start+7] << 56);
        return l;
    }



    //字节到浮点转换
    public static double byteToDouble(byte[] b) {
        long l;

        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;

        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);


        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    //把char 转换成无符号数
    public static int charToUnsign(char c) {
        int m2;
        m2 = (int) ((c + 256) % 256);
        return m2;
    }

    public static void main(String args[]){
              long hhb= 123456789;
              byte[] b=BaseTypeConvers.longToByte(hhb);
              long r= BaseTypeConvers.byteToLong(b);
              //System.out.println(r);
    }
}
