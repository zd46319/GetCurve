package app;

import java.io.IOException;

import bo.GetZYSHGCurve;
import bo.GetZZSYLCurve;


import common.CommonFunctions;
import common.SendGet;
import common.ExportExcel;
import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ActionMessage;
import flex.messaging.messages.Message;
import flex.messaging.messages.RemotingMessage;

public class test {
	  public static void main(String[] args) {

	       // TODO Auto-generated method stub

	       int i = 0;

	       for(i=0;i<5;i++)

	       {

	           System.out.println(i);

	       }

	       //�������С�Ķ���һ��--i;

	       --i;

	       assert i==5:"error";     

	    }

	public static String str="TEST";
	/*public static void main(String[] args) {
		//��Ѻʽ�ع�
		GetZYSHGCurve gc=new GetZYSHGCurve();
		gc.getZYSHG("20150101","20150110");
		//��ծ����������
		GetZZSYLCurve gc= new GetZZSYLCurve();
		
		//System.out.println("D1002,D1003".indexOf(""));
		
		str="TEST1";
		changeStr(str);
		str.substring(1);
		System.out.println(str);
		
	}*/
	public static void changeStr(String str){
		str="DHCC";
	}
}
