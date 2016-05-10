package common;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonFunctions {
	/**
     * 返回两日期相减的结果天数，负数表示dateLong1在dateLong2前。dateLong1格式为"yyyymmdd"
     * @param dateStr1 long  被减数日期字符串
     * @param dateStr2 long 减数日期字符串
     * @return int dateLong1-dateLong2的天数
     */
    public static int daysSubtract(long dateLong1, long dateLong2) {
    	  
    	  String dateStr1 = String.valueOf(dateLong1);
    	  String dateStr2 =String.valueOf(dateLong2);
    	  
    	  int year1 = Integer.parseInt(dateStr1.substring(0, 4));
    	  int month1 = Integer.parseInt(dateStr1.substring(4, 6));
    	  int day1 = Integer.parseInt(dateStr1.substring(6, 8));
    	  int year2 = Integer.parseInt(dateStr2.substring(0, 4));
    	  int month2 = Integer.parseInt(dateStr2.substring(4, 6));
    	  int day2 = Integer.parseInt(dateStr2.substring(6, 8));
    	  Calendar c1 = Calendar.getInstance();
    	  c1.set(Calendar.YEAR, year1);
    	  c1.set(Calendar.MONTH, month1 - 1);
    	  c1.set(Calendar.DAY_OF_MONTH, day1);
    	  Calendar c2 = Calendar.getInstance();
    	  c2.set(Calendar.YEAR, year2);
    	  c2.set(Calendar.MONTH, month2 - 1);
    	  c2.set(Calendar.DAY_OF_MONTH, day2);
    	  long mills =c1.getTimeInMillis() - c2.getTimeInMillis();
    	  return (int) (mills / 1000 / 3600 / 24);
    }
    
    
    /**
     * 按天数修改日期
     * @param baseDateLong (long) 基准日期long 8位 (格式yyyyMMdd)
     * @param changeDays  (int) 要增加的天数（负数为减）
     * @return (long) 基准日期增加或减少若干天后的日期(格式yyyyMMdd)
     */
    public static long pub_base_deadlineD(long baseDateLong,int changeDays){   	
    	long ResultDateLong=0;
    	
    	String baseDate=String.valueOf(baseDateLong);
        if(baseDate.length()!=8){
    	    return 0;
    	}
    	StringBuffer sb = new StringBuffer();
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // 将字符串格式化
        Date dt=sdf.parse(baseDate,new ParsePosition(0)); // 由格式化后的字符串产生一个Date对象
       
        Calendar c = Calendar.getInstance(); // 初始化一个Calendar
        
        try{
    	    c.setTime(dt); // 设置基准日期
        }catch(Exception e){
    	    return 0;
    	    //e.printStackTrace();
        }
        
        c.add(Calendar.DATE, changeDays); //你要加减的日期 
        Date dt1=c.getTime(); // 从Calendar对象获得基准日期增减后的日期
        sb=sdf.format(dt1,sb,new FieldPosition(0)); // 得到结果字符串        
        String ResultDateStr=sb.toString();
        
        ResultDateLong=Long.parseLong(ResultDateStr);
    	return ResultDateLong;
    }
}
