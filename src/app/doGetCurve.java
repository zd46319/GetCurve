package app;

import bo.GetZYSHGCurve;
import bo.GetZZSYLCurve;

public class doGetCurve {
	public static void main(String[] args) {
		//获取质押式回购利率
		//getZYSHG("20150101","20150110");
		//获取中债收益率曲线数据
		getZzsyl("20151001","20151030","yp");
	}
	/**
	 * 获取质押式回购利率
	 * @param strStartDate
	 * @param strEndDate
	 * 
	 * */
	public static void getZYSHG(String strStartDate,String strEndDate){
		//获取质押式回购利率
		GetZYSHGCurve gc=new GetZYSHGCurve();
		gc.getZYSHG(strStartDate,strEndDate);
	}
	
	/**
	 * 获取中债收益率数据。单曲线
	 * @param strStartDate
	 * @param strEndDate
	 * 
	 * */
	public static void getZzsyl(String strStartDate,String strEndDate,String type){
		//获取质押式回购利率
		GetZZSYLCurve gc=new GetZZSYLCurve();
		gc.getCurveDataByDate(strStartDate,strEndDate,type);
	}
	
	
}
