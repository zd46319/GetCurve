/**
 * 获取质押式回购的利率
 * http://www.chinabond.com.cn/jsp/include/EJB/query_jm.jsp?ZQDMMDS=&sel3=1&tbSelYear3=2015&tbSelMonth3=10&calSeledDate3=13&queryType=allprice&queryType1=huigou&sZQFXR1=0&sFXFS1=0&ZQXZ=00&sZQFXR2=0&ZQFXRMDS=00&PriceTypeMDS=1&PriceTypeMDS1=1&ZQFXRMDS1=00&HGPZMDS=00
 * @author zd-dhcc
 * */
package bo;

import java.io.IOException;

import common.CommonFunctions;
import common.SendGet;
import common.ExportExcel;

public class GetZYSHGCurve {

	private static SendGet sg=new SendGet();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetZYSHGCurve gc=new GetZYSHGCurve();
		//------------质押式回购数据获取---------------
		//double[][] data=getZYHGdata("20141101","20141130");
		/*for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				if(data[i][0]==0){
					System.out.println("000000");
					continue;
				}
				System.out.println("i:"+i+";j:"+j+";data:"+data[i][j]);
			}
		}*/
		//------------质押式回购数据获取end---------------
		//------------利率曲线数据获取--------------------
		gc.getZYSHG("20150101","20150110");
	}
	
	//获取质押式回购相关利率
	public void getZYSHG(String startDate,String endDate){
		// TODO Auto-generated method stub
		GetZYSHGCurve gc=new GetZYSHGCurve();
		startDate="20151205";
		endDate="20151206";
		//获取相关的利率数据
		double[][] data=getZYHGdata(startDate,endDate);
		//导出
		ExportExcel e=new ExportExcel();
		e.doExportNoTitle(startDate,endDate,"ZYSHG",new String[]{"日期","O/N","1W","2W","1M","3M","6M"},data);
		//System.out.println("sub:"+CommonFunctions.daysSubtract(20141231, 20141201));
	}
	
	
	
	/**
	 * 获取质押式回购网络数据
	 * @return 每日各个期限的数据
	 */
	public  double[][] getZYHGdata(String StrstartDate,String StrendDate){
		long startDate = Long.valueOf(StrstartDate);
		long endDate = Long.valueOf(StrendDate);
		int int_loop = CommonFunctions.daysSubtract(endDate, startDate);
		double[][] data=new double[int_loop+1][6];
		for (int i = 0; i <= int_loop; i++) {
			long newTodayDate = CommonFunctions.pub_base_deadlineD(startDate, i);
			String content=getZYSHGUrlContentByDay(String.valueOf(newTodayDate));//获取网页内容
			double[] data_Day=getZYSHGdataByHtml(content);
			if(data_Day!=null){
				data[i]=data_Day;
			}
			System.out.println("获取"+newTodayDate+"数据成功");
		}
		
		return data;
	}
	
	
	/**
	 * 获取质押式回购的相关数据。返回网页内容
	 * date 获取数据的日期 格式 yyyymmdd  20141103
	 * **/
	
	public  String getZYSHGUrlContentByDay(String date){
		String year=date.substring(0,4);
		String month=date.substring(4,6);
		if(month.substring(0,1).equals("0")){
			month=month.substring(1);
		}
		String day=date.substring(6);
		if(day.substring(0,1).equals("0")){
			day=day.substring(1);
		}
		sg.setSendUrl("http://www.chinabond.com.cn/jsp/include/EJB/query_jm.jsp?ZQDMMDS=&sel3=1&tbSelYear3="+year+"&tbSelMonth3="+month+"&calSeledDate3="+day+"&queryType=allprice&queryType1=huigou&sZQFXR1=0&sFXFS1=0&ZQXZ=00&sZQFXR2=0&ZQFXRMDS=00&PriceTypeMDS=1&PriceTypeMDS1=1&ZQFXRMDS1=00&HGPZMDS=00");
		//e.setSendParm("ZQDMMDS=&sel3=1&tbSelYear3=2014&tbSelMonth3=11&calSeledDate3=1&queryType=allprice&queryType1=huigou&sZQFXR1=0&sFXFS1=0&ZQXZ=00&sZQFXR2=0&ZQFXRMDS=00&PriceTypeMDS=1&PriceTypeMDS1=1&ZQFXRMDS1=00&HGPZMDS=00");
		sg.setSendCodeing("GB2312");
		sg.setSendMethod("Get");
		String urlcontent="";
		try {
			urlcontent=sg.getContent();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(urlcontent==null || urlcontent.trim().equals("")){
			System.out.println("获取网页内容为空！");
			return "";
		}else{
			return urlcontent;
		}
	}
	/**
	 * 根据网页内容，获取相关数据 1天 一周二周 1个月，3个月，6个月
	 * urlcontent 网页内容
	 * **/
	
	public  double[] getZYSHGdataByHtml(String urlcontent){
		double[] data=new double[6];
		int begin=urlcontent.indexOf("通用页头 结束");
		int end=urlcontent.indexOf("通用页尾 开始");
		urlcontent=urlcontent.substring(begin,end);
		if(urlcontent.contains("/CB_CN/images/tc_tishi.jpg")){
			System.out.println("该日无相关数据");
			return null;
		}else{
			//========================获取相关不同期限的值
			//截取数据相关内容
			begin=urlcontent.indexOf("R01D");
			end=urlcontent.indexOf("R09M");
			urlcontent=urlcontent.substring(begin<0?0:begin,end<0?urlcontent.length():end);
			//根据期限分组
			String[] dataStr=urlcontent.split("<tr");
			for(int i=0;i<dataStr.length;i++){
				dataStr[i]=dataStr[i].replaceAll("(\r\n|\r|\n|\n\r)", "");//去除回车换行
				if(i!=0){
					dataStr[i]=dataStr[i].substring(dataStr[i].indexOf(">R")+1);//保证开始为期限值
				}
				//去除网页上其他干扰数据--------------
				dataStr[i]=dataStr[i].replaceAll("(</td>[^>]+>)", "##");
				if(dataStr[i].split("##")[0].equals("R01D")){
					data[0]=Double.valueOf(dataStr[i].split("##")[5].contains("---")?dataStr[i].split("##")[8]:dataStr[i].split("##")[5]);
					continue;
				}
				if(dataStr[i].split("##")[0].equals("R07D")){
					data[1]=Double.valueOf(dataStr[i].split("##")[5].contains("---")?dataStr[i].split("##")[8]:dataStr[i].split("##")[5]);
					continue;
				}
				if(dataStr[i].split("##")[0].equals("R14D")){
					data[2]=Double.valueOf(dataStr[i].split("##")[5].contains("---")?dataStr[i].split("##")[8]:dataStr[i].split("##")[5]);
					continue;
				}
				if(dataStr[i].split("##")[0].equals("R01M")){
					data[3]=Double.valueOf(dataStr[i].split("##")[5].contains("---")?dataStr[i].split("##")[8]:dataStr[i].split("##")[5]);
					continue;
				}
				if(dataStr[i].split("##")[0].equals("R03M")){
					data[4]=Double.valueOf(dataStr[i].split("##")[5].contains("---")?dataStr[i].split("##")[8]:dataStr[i].split("##")[5]);
					continue;
				}
				if(dataStr[i].split("##")[0].equals("R06M")){
					data[5]=Double.valueOf(dataStr[i].split("##")[5].contains("---")?dataStr[i].split("##")[8]:dataStr[i].split("##")[5]);
					continue;
				}
				//System.out.println(i+":====="+dataStr[i]);  
			}//end for 循环的不同期限：for(int i=0;i<dataStr.length;i++){
			
			
			return data;
		}
		
	}
	
	
}
