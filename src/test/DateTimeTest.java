package test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTest {

	public static void main(String[] args) {

        String MSG_TIMESTAMP_FORMAT = "yyyyMMdd'T'HHmmss'Z'";

        SimpleDateFormat df = new SimpleDateFormat(MSG_TIMESTAMP_FORMAT);

        
        String dt =  df.format(new Date());
        
        System.out.println(dt);

        String str = "2013-01-21 15:10:20";  
        Date date = null;  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
            date = sdf.parse(str);  
        } catch (ParseException e) {  
            System.out.println(e.getMessage());  
        }  
        System.out.println(date);  
        System.out.println(date.getTime());
        
	}

}
