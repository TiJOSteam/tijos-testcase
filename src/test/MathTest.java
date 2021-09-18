package test;


class MathUtil   
{   
  public static int devide(int x, int y)   
  {   
    float f = x;   
    return Math.round(f / y);   
  }   
  
  public static long devide(long x, long y)   
  {   
    double d = x;   
    return Math.round(d / y);   
  }   
  
  public static float round(float f, int bit)   
  {   
    float p = (float)pow(bit);   
    return (Math.round(f * p) / p);   
  }   
  
  public static double round(double d, int bit)   
  {   
    double p = pow(bit);   
    return (Math.round(d * p) / p);   
  }   
  
  private static long pow(int bit)   
  {   
    return Math.round(Math.pow(10.0D, bit));   
  }   
} 

public class MathTest {   
    public static void main(String[] args) {   
        System.out.println("Math.round(11.5)=" + Math.round(11.5));   
        System.out.println("Math.round(-11.5)=" + Math.round(-11.5));   
        System.out.println();   
  
        System.out.println("Math.round(11.46)=" + Math.round(11.46));   
        System.out.println("Math.round(-11.46)=" + Math.round(-11.46));   
        System.out.println();   
  
        System.out.println("Math.round(11.68)=" + Math.round(11.68));   
        System.out.println("Math.round(-11.68)=" + Math.round(-11.68));   
        
        
        System.out.println(MathUtil.round(0.0099999f, 3));
        
    }   
}  