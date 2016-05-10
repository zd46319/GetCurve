/**
 * ��ȡ��Ѻʽ�ع�������
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
		//------------��Ѻʽ�ع����ݻ�ȡ---------------
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
		//------------��Ѻʽ�ع����ݻ�ȡend---------------
		//------------�����������ݻ�ȡ--------------------
		gc.getZYSHG("20150101","20150110");
	}
	
	//��ȡ��Ѻʽ�ع��������
	public void getZYSHG(String startDate,String endDate){
		// TODO Auto-generated method stub
		GetZYSHGCurve gc=new GetZYSHGCurve();
		startDate="20151205";
		endDate="20151206";
		//��ȡ��ص���������
		double[][] data=getZYHGdata(startDate,endDate);
		//����
		ExportExcel e=new ExportExcel();
		e.doExportNoTitle(startDate,endDate,"ZYSHG",new String[]{"����","O/N","1W","2W","1M","3M","6M"},data);
		//System.out.println("sub:"+CommonFunctions.daysSubtract(20141231, 20141201));
	}
	
	
	
	/**
	 * ��ȡ��Ѻʽ�ع���������
	 * @return ÿ�ո������޵�����
	 */
	public  double[][] getZYHGdata(String StrstartDate,String StrendDate){
		long startDate = Long.valueOf(StrstartDate);
		long endDate = Long.valueOf(StrendDate);
		int int_loop = CommonFunctions.daysSubtract(endDate, startDate);
		double[][] data=new double[int_loop+1][6];
		for (int i = 0; i <= int_loop; i++) {
			long newTodayDate = CommonFunctions.pub_base_deadlineD(startDate, i);
			String content=getZYSHGUrlContentByDay(String.valueOf(newTodayDate));//��ȡ��ҳ����
			double[] data_Day=getZYSHGdataByHtml(content);
			if(data_Day!=null){
				data[i]=data_Day;
			}
			System.out.println("��ȡ"+newTodayDate+"���ݳɹ�");
		}
		
		return data;
	}
	
	
	/**
	 * ��ȡ��Ѻʽ�ع���������ݡ�������ҳ����
	 * date ��ȡ���ݵ����� ��ʽ yyyymmdd  20141103
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
			System.out.println("��ȡ��ҳ����Ϊ�գ�");
			return "";
		}else{
			return urlcontent;
		}
	}
	/**
	 * ������ҳ���ݣ���ȡ������� 1�� һ�ܶ��� 1���£�3���£�6����
	 * urlcontent ��ҳ����
	 * **/
	
	public  double[] getZYSHGdataByHtml(String urlcontent){
		double[] data=new double[6];
		int begin=urlcontent.indexOf("ͨ��ҳͷ ����");
		int end=urlcontent.indexOf("ͨ��ҳβ ��ʼ");
		urlcontent=urlcontent.substring(begin,end);
		if(urlcontent.contains("/CB_CN/images/tc_tishi.jpg")){
			System.out.println("�������������");
			return null;
		}else{
			//========================��ȡ��ز�ͬ���޵�ֵ
			//��ȡ�����������
			begin=urlcontent.indexOf("R01D");
			end=urlcontent.indexOf("R09M");
			urlcontent=urlcontent.substring(begin<0?0:begin,end<0?urlcontent.length():end);
			//�������޷���
			String[] dataStr=urlcontent.split("<tr");
			for(int i=0;i<dataStr.length;i++){
				dataStr[i]=dataStr[i].replaceAll("(\r\n|\r|\n|\n\r)", "");//ȥ���س�����
				if(i!=0){
					dataStr[i]=dataStr[i].substring(dataStr[i].indexOf(">R")+1);//��֤��ʼΪ����ֵ
				}
				//ȥ����ҳ��������������--------------
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
			}//end for ѭ���Ĳ�ͬ���ޣ�for(int i=0;i<dataStr.length;i++){
			
			
			return data;
		}
		
	}
	
	
}
