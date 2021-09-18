package crypto;

public class CRC16 {

    public static String CRC16_GENIBUS(int[] buffer) {
        int wCRCin = 0xFFFF;
        int wCPoly = 0x1021;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0xFFFF;

        return convertToHexString(wCRCin);
    }

    /**
     * CRC-CCITT (0xFFFF)
     * CRC16_CCITT_FALSE：多项式x16+x12+x5+1（0x1021），初始值0xFFFF，低位在后，高位在前，结果与0x0000异或
     *
     * @param buffer
     * @return
     */
    public static String CRC16_CCITT_FALSE(int[] buffer) {
        int wCRCin = 0xFFFF;
        int wCPoly = 0x1021;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;

        return convertToHexString(wCRCin);
    }


    public static String CRC16_ARC(int[] buffer) {
        int wCRCin = revertBit(0x0000);
        int POLYNOMIAL = revertBit(0x8005); //8005 逆Bit序 A001
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }

    public static String CRC16_AUG_CCITT(int[] buffer) {
        int wCRCin = 0x1D0F;
        int wCPoly = 0x1021;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_BUYPASS(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x8005;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_CDMA2000(int[] buffer) {
        int wCRCin = 0xFFFF;
        int wCPoly = 0xC867;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_DDS_110(int[] buffer) {
        int wCRCin = 0x800D;
        int wCPoly = 0x8005;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_DECT_R(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x0589;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0001;
        return convertToHexString(wCRCin);
    }

    public static String CRC16_DECT_X(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x0589;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_DNP(int[] buffer) {
        int wCRCin = revertBit(0x0000);
        int POLYNOMIAL = revertBit(0x3D65);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0xFFFF;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_EN_13757(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x3D65;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0xFFFF;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_MAXIM(int[] buffer) {
        int wCRCin = revertBit(0x0000);
        int POLYNOMIAL = revertBit(0x8005);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0xFFFF;
        return convertToHexString(wCRCin);
    }

    //RIELLO
    public static String CRC16_RIELLO(int[] buffer) {
        int wCRCin = revertBit(0xB2AA);
        int POLYNOMIAL = revertBit(0x1021);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    //MCRF4XX
    public static String CRC16_MCRF4XX(int[] buffer) {
        int wCRCin = revertBit(0xFFFF);
        int POLYNOMIAL = revertBit(0x1021);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }





    //T10-DIF
    public static String CRC16_T10_DIF(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x8BB7;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }

    //TELEDISK
    public static String CRC16_TELEDISK(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0xA097;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }

    //TMS37157
    public static String CRC16_TMS37157(int[] buffer) {
        int wCRCin = revertBit(0x89EC);
        int POLYNOMIAL = revertBit(0x1021);

        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }

    //USB
    public static String CRC16_USB(int[] buffer) {
        int wCRCin = revertBit(0xFFFF);
        int POLYNOMIAL = revertBit(0x8005);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0xFFFF;
        return convertToHexString(wCRCin);
    }

    //A
    public static String CRC16_A(int[] buffer) {
        int wCRCin = revertBit(0xC6C6);
        int POLYNOMIAL = revertBit(0x1021);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }

    //KERMIT
    public static String CRC16_KERMIT(int[] buffer) {
        int wCRCin = revertBit(0x0000);
        int POLYNOMIAL = revertBit(0x1021);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }

    public static String CRC16_MODBUS(int[] buffer) {
        int wCRCin = revertBit(0xFFFF);
        int POLYNOMIAL = revertBit(0x8005);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    //X-25
    public static String CRC16_X_25(int[] buffer) {
        int wCRCin = revertBit(0xFFFF);
        int POLYNOMIAL = revertBit(0x1021);
        for (int b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0xFFFF;
        return convertToHexString(wCRCin);
    }

    //XMODEM
    public static String CRC16_XMODEM(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x1021;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    public static String CRC16_8005(int[] buffer) {
        int wCRCin = 0x0000;
        int wCPoly = 0x8005;
        for (int b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = (((b&0xFFFF) >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit)
                    wCRCin ^= wCPoly;
            }
        }
        wCRCin &= 0xffff;
        wCRCin ^= 0x0000;
        return convertToHexString(wCRCin);
    }


    /**
     * 翻转16位的高八位和低八位字节
     *
     * @param src 翻转数字
     * @return 翻转结果
     */
    private static int revert(int src) {
        int lowByte = (src & 0xFF00) >> 8;
        int highByte = (src & 0x00FF) << 8;
        return lowByte | highByte;
    }

    private static int revertBit(int src) {
        int tmp = 0;

        for (int i = 0; i < 16; i++)
        {
            tmp = tmp << 1;
            tmp = ((src>>i)&0x0001)|tmp;
        }

        tmp &=0xFFFF;
        return tmp;
    }

    private static String convertToHexString(int src) {

        return String.format("%04X", src);
        //return Integer.toHexString(src).toUpperCase();
    }


    public static void main(String[] args)
    {
        int[] data1 = new int[]{
                0xCB,0x74,0x7F,0xD2,0x56,0x3A,0x8C,0xD4,0xB6,0x93,0xD5,0xA1,0x0C,0x0C,0xFE,0x42,
                0x89,0xD6,0x3D,0xAD,0xA7,0x5F,0x07,0x30,0xCE,0x38,0xB9,0xF8,0x14,0x54,0xB6,0xAF
        };
        int[] data = new int[]{0x80,0xF9,0x00,0x00};


        System.out.println("CRC16_MCRF4XX       "+CRC16.CRC16_MCRF4XX(data));
        System.out.println("CRC16_CCITT_FALSE   "+CRC16.CRC16_CCITT_FALSE(data));
        System.out.println("CRC16_ARC           "+CRC16.CRC16_ARC(data));
        System.out.println("CRC16_AUG_CCITT     "+CRC16.CRC16_AUG_CCITT(data));
        System.out.println("CRC16_BUYPASS       "+CRC16.CRC16_BUYPASS(data));
        System.out.println("CRC16_CDMA2000      "+CRC16.CRC16_CDMA2000(data));
        System.out.println("CRC16_DDS_110       "+CRC16.CRC16_DDS_110(data));
        System.out.println("CRC16_DECT_R        "+CRC16.CRC16_DECT_R(data));
        System.out.println("CRC16_DECT_X        "+CRC16.CRC16_DECT_X(data));
        System.out.println("CRC16_DNP           "+CRC16.CRC16_DNP(data));
        System.out.println("CRC16_EN_13757      "+CRC16.CRC16_EN_13757(data));
        System.out.println("CRC16_GENIBUS       "+CRC16.CRC16_GENIBUS(data));
        System.out.println("CRC16_MAXIM         "+CRC16.CRC16_MAXIM(data));
        System.out.println("CRC16_RIELLO        "+CRC16.CRC16_RIELLO(data));
        System.out.println("CRC16_T10_DIF       "+CRC16.CRC16_T10_DIF(data));
        System.out.println("CRC16_TELEDISK      "+CRC16.CRC16_TELEDISK(data));
        System.out.println("CRC16_TMS37157      "+CRC16.CRC16_TMS37157(data));
        System.out.println("CRC16_USB           "+CRC16.CRC16_USB(data));
        System.out.println("CRC16_A             "+CRC16.CRC16_A(data));
        System.out.println("CRC16_KERMIT        "+CRC16.CRC16_KERMIT(data));
        System.out.println("CRC16_MODBUS        "+CRC16.CRC16_MODBUS(data));
        System.out.println("CRC16_X_25          "+CRC16.CRC16_X_25(data));
        System.out.println("CRC16_XMODEM        "+CRC16.CRC16_XMODEM(data));
    }
}
