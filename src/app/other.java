package app;

import java.io.IOException;

import common.ExportExcel;
import common.SendGet;

public class other {
	
	private static SendGet sg=new SendGet();
	
	public static void main(String[] args) {
	//	getpedaily();
	}
	
	//longtu
	public static void getlongtu(){
		sg.setSendUrl("http://d.longtugame.com/d/hd150804getCinfo");
		sg.setSendCodeing("GB2312");
		sg.setSendMethod("Get");
		String urlcontent="";
		try {
			boolean getcheck=true;
			for(int i=0;i<100000;i++){
				urlcontent=sg.getContent();
				getcheck=urlcontent.matches(":200");
				System.out.println(i+":"+urlcontent);
				if(getcheck){
					break;
				}else{
					Thread.sleep(1000);
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
