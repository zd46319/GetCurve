package workfromhome;

import java.io.IOException;

import common.ExportExcel;
import common.SendGet;

public class cantonfair {
	private static SendGet sg=new SendGet();
	
	public static void main(String[] args) {
		//	getpedaily();
		}
	
	//get itemList 
	//1 家用电器 http://i.cantonfair.org.cn/cn/expexhibitorlist.aspx?categoryno=410
	public static String[][][] getItemList(){
		sg.setSendUrl("http://i.cantonfair.org.cn/cn/index.aspx");
		sg.setSendCodeing("utf-8");
		sg.setSendMethod("Get");
		String urlcontent="";
		String[][][] data=new String[3][30][2];
		try {
			urlcontent=sg.getContent();
			String[] getList=urlcontent.split("<div class=\"phase\">");
			for(int i=0;i<getList.length-1;i++){
				String listData=getList[i+1];
				String[] itemData=listData.split("<a");
				for(int j=0;j<itemData.length;j++){
					String itemUrl=itemData[j].substring(0,itemData[j].indexOf("\"  class=\"ico")).substring(itemData[j].indexOf("href")+6);
					String itemName=itemData[j].substring(0,itemData[j].indexOf("</a>")).substring(itemData[j].indexOf("class=\"ico"));
					itemName=itemName.substring(itemName.indexOf("\">")+2).replaceAll("br", "").replaceAll("<", "").replaceAll("/>", "").replaceAll(" ", "");
					data[i][j][0]=itemName;
					data[i][j][1]=itemUrl;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	//get url list all
	//页数，序号，类型，中文名称，英文名称
	public static void getcantonfair(String url){
		sg.setSendUrl("http://"+url);
		sg.setSendCodeing("utf-8");
		sg.setSendMethod("Get");
		String urlcontent="";
		try {
			urlcontent=sg.getContent();
			//System.out.println(urlcontent);
			String getList=urlcontent.substring(0,urlcontent.indexOf("<div id=\"pager\">")).substring(urlcontent.indexOf("<div class=\"exhibitor_list\">"));
			String ListCount=getList.substring(getList.indexOf("total")+7,getList.indexOf("</span>"));
			System.out.println("总条数"+ListCount);
			int dataCount=0;
			int pageCount=412;
			String[] getDatas=getList.split("<li>");
			String[][] data =new String[Integer.valueOf(ListCount)+1000][6];
			for(int j=0;j<pageCount;j++){
				urlcontent="";
				getList="";
				if(j>0){
					sg.setSendUrl("http://zdb.pedaily.cn/company/all/"+String.valueOf(j+1));
					sg.setSendCookie("__uid=1657394316; __fromtype=0; __utmt=1; Hm_lvt_25919c38fb62b67cfb40d17ce3348508=1453855555,1453942494; Hm_lpvt_25919c38fb62b67cfb40d17ce3348508=1453955383; __utma=23980325.860528497.1453707206.1453946552.1453953905.5; __utmb=23980325.15.10.1453953905; __utmc=23980325; __utmz=23980325.1453855555.2.2.utmcsr=newsmth.net|utmccn=(referral)|utmcmd=referral|utmcct=/nForum/");
					urlcontent=sg.getContent();
					getList=urlcontent.substring(urlcontent.indexOf("amount-search"));
					getList=getList.substring(0,getList.indexOf("box-page"));
					getDatas=getList.split("<li>");
				}
				for(int i=1;i<getDatas.length;i++){
					int nowdata=dataCount++;
					String getName=getDatas[i].substring(getDatas[i].indexOf("class=\"f16\"")+12);
					getName=getName.substring(0,getName.indexOf("</a>"));
					String getType=getDatas[i].substring(getDatas[i].indexOf("<span>")+6).replaceAll(" ", "");
					getType=getType.substring(0,getType.indexOf("</span>"));
					String getShortName=getDatas[i].substring(getDatas[i].indexOf("简称：<"));
					if(getShortName.indexOf(">")<0||getShortName.indexOf("</a>")<0){
						getShortName="";
					}else{
						try{
							getShortName=getShortName.substring(getShortName.indexOf(">")+1,getShortName.indexOf("</a>"));
						}catch(Exception e){
							System.out.println("error:"+getShortName);
						}
					
					}
					String getValue=getDatas[i].substring(getDatas[i].indexOf("简称："));
					getValue=getValue.substring(getValue.indexOf("<div>")+5,getValue.indexOf("</div>")).replaceAll("\n", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\\?", "");
					System.out.println(j+","+nowdata+","+getName+","+getType+","+getShortName+","+getValue);
					data[nowdata][0]=String.valueOf(j+1);
					data[nowdata][1]=String.valueOf(nowdata+1);
					data[nowdata][2]=getName;
					data[nowdata][3]=getType;
					data[nowdata][4]=getShortName;
					data[nowdata][5]=getValue;
				}//end i
			}//end j
			
			//export
			
			ExportExcel e=new ExportExcel();
			e.doExportNoTitle("投资界List","投资界List",new String[]{"页数","序号","名称","类型","简称","简介"},data);
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
