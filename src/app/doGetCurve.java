package app;

import bo.GetZYSHGCurve;
import bo.GetZZSYLCurve;

public class doGetCurve {
	public static void main(String[] args) {
		//��ȡ��Ѻʽ�ع�����
		//getZYSHG("20150101","20150110");
		//��ȡ��ծ��������������
		getZzsyl("20151001","20151030","yp");
	}
	/**
	 * ��ȡ��Ѻʽ�ع�����
	 * @param strStartDate
	 * @param strEndDate
	 * 
	 * */
	public static void getZYSHG(String strStartDate,String strEndDate){
		//��ȡ��Ѻʽ�ع�����
		GetZYSHGCurve gc=new GetZYSHGCurve();
		gc.getZYSHG(strStartDate,strEndDate);
	}
	
	/**
	 * ��ȡ��ծ���������ݡ�������
	 * @param strStartDate
	 * @param strEndDate
	 * 
	 * */
	public static void getZzsyl(String strStartDate,String strEndDate,String type){
		//��ȡ��Ѻʽ�ع�����
		GetZZSYLCurve gc=new GetZZSYLCurve();
		gc.getCurveDataByDate(strStartDate,strEndDate,type);
	}
	
	
}
