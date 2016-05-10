/**
 * 中债收益率曲线
 * http://yield.chinabond.com.cn/cbweb-mn/messagebroker/amf
 * amf包获取后进行解码
 * 
 * */
package bo;


import com.cb.common.biz.yield.domain.YcChartData;

import common.CommonFunctions;
import common.DoAmf;
import common.ExportExcel;
import common.SendGet;
import flex.messaging.io.ArrayCollection;

public class GetZZSYLCurve {
	DoAmf doamf =new DoAmf();
	
	public static void main(String[] args) {
		//中债收益率曲线
		GetZZSYLCurve gc= new GetZZSYLCurve();
		double[][] data=gc.getSingleCurveByDate("yp","20151001","20151030",new Double[]{0.0,0.25,0.5,0.75,1.0});
		System.out.println("datalength:"+data.length);
	}
	
	public void getCurveDataByDate(String startDate,String endDate,String type){//"20151001","20151030","yp"
		// TODO Auto-generated method stub
		GetZZSYLCurve gc= new GetZZSYLCurve();
		//获取相关的利率数据
		double[][] data=gc.getSingleCurveByDate(type,startDate,endDate,new Double[]{0.0,0.25,0.5,0.75,1.0});
		//导出
		ExportExcel e=new ExportExcel();
		e.doExportNoTitle(startDate,endDate,type,new String[]{"日期","O/N","3M","6M","9M","1Y"},data);
		//System.out.println("sub:"+CommonFunctions.daysSubtract(20141231, 20141201));
	}
	
	
	/**
	 * 获取相关收益率曲线内容，单曲线多日期
	 *  曲线类型，起始日期，结束日期
	 * @param String curveType 获取数据类型 zcxjrz：政策性金融债，gz：国债，ptzAA-:AA-银行普通债,yp:央票
	 * @param StrstartDate 日期，格式为yyyymmdd
	 * @param StrendDate 日期，格式为yyyymmdd
	 * @param term 各个期限，年为单位，保留四位小数
	 * 节假日的数据为零
	 * @return double[][] data 按照日期排列
	 * **/
	public double[][] getSingleCurveByDate(String curveType,String startDate,String endDate,Double[] term ){
		int daycnt=CommonFunctions.daysSubtract(Long.valueOf(endDate), Long.valueOf(startDate))+1;
		double[][] data=new double[daycnt][term.length];
		SendGet sg=new SendGet(); 
		/*curveType="yp";
		startDate="20151001";
		endDate="20151030";*/
		sg.setItem(curveType);
		sg.setStrstartDate(startDate);
		sg.setStrendDate(endDate);
		ArrayCollection yc=sg.getAmfContent("0");
		for(int i=0;i<yc.size();i++){
			ArrayCollection datas=(ArrayCollection)((YcChartData) yc.get(i)).getSeriesData();
			String workTime=((YcChartData) yc.get(i)).getWorktime().replaceAll("-", "");
			int dayIdx=CommonFunctions.daysSubtract(Long.valueOf(workTime), Long.valueOf(startDate));
			for(int j=0;j<datas.size();j++){
				Object[] getdata=(Object[]) datas.get(j);
				String StrTerm=getdata[0].toString();
				Double dbTerm=Double.valueOf(StrTerm);
				for(int k=0;k<term.length;k++){
					if(dbTerm.doubleValue()==term[k].doubleValue()){
						data[dayIdx][k]=Double.valueOf(getdata[1].toString());
					}
				}
			}
		}
		return data;
	}
}
