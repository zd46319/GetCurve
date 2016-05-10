package common;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonFunctions {
	/**
     * ��������������Ľ��������������ʾdateLong1��dateLong2ǰ��dateLong1��ʽΪ"yyyymmdd"
     * @param dateStr1 long  �����������ַ���
     * @param dateStr2 long ���������ַ���
     * @return int dateLong1-dateLong2������
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
     * �������޸�����
     * @param baseDateLong (long) ��׼����long 8λ (��ʽyyyyMMdd)
     * @param changeDays  (int) Ҫ���ӵ�����������Ϊ����
     * @return (long) ��׼�������ӻ����������������(��ʽyyyyMMdd)
     */
    public static long pub_base_deadlineD(long baseDateLong,int changeDays){   	
    	long ResultDateLong=0;
    	
    	String baseDate=String.valueOf(baseDateLong);
        if(baseDate.length()!=8){
    	    return 0;
    	}
    	StringBuffer sb = new StringBuffer();
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
        Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
       
        Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
        
        try{
    	    c.setTime(dt); // ���û�׼����
        }catch(Exception e){
    	    return 0;
    	    //e.printStackTrace();
        }
        
        c.add(Calendar.DATE, changeDays); //��Ҫ�Ӽ������� 
        Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
        sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���        
        String ResultDateStr=sb.toString();
        
        ResultDateLong=Long.parseLong(ResultDateStr);
    	return ResultDateLong;
    }
}
