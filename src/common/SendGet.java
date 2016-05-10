package common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.cb.common.biz.yield.domain.YcChartData;

import flex.messaging.io.ArrayCollection;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.ActionMessage;
import flex.messaging.io.amf.AmfMessageSerializer;
import flex.messaging.io.amf.AmfTrace;
import flex.messaging.io.amf.client.AMFConnection;
import flex.messaging.io.amf.client.exceptions.ClientStatusException;
import flex.messaging.io.amf.client.exceptions.ServerStatusException;
import flex.messaging.messages.AcknowledgeMessage;
import flex.messaging.messages.RemotingMessage;

/**
 * @author zd-dhcc
 */
public class SendGet {

	// UrlInfo sbo = new SendInfoBo();

	private static URLConnection conn;
	private static URL sendUrl;
	private static String sendParm;
	private static String sendCookie;
	private static String sendMethod;
	private static String sendCodeing;
	private String[] items;
	private  String item;
	private  String queryDate;
	private  String StrstartDate;
	private  String StrendDate;

	public SendGet() {
	}

	public static void main(String[] args) {
		SendGet sg=new SendGet();
		sg.setItem("gz");
		sg.setStrendDate("20150701");
		sg.setStrstartDate("20150710");
		ArrayCollection yc=sg.getAmfContent("0");
		for(int i=0;i<yc.size();i++){
			System.out.println(((YcChartData) yc.get(i)).getWorktime()+((YcChartData) yc.get(i)).getYcDefName()+"数据条数:"+((YcChartData) yc.get(i)).getSeriesData().size());
		}
	}

	// for method=post
	public static void sendPost() throws IOException {

		conn = sendUrl.openConnection();

		conn.setDoInput(true);
		conn.setDoOutput(true);
		PrintWriter output;
		try {
			output = new PrintWriter(conn.getOutputStream());
			output.print(sendParm);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getContent() throws IOException {
		String line, result = "";

		conn = sendUrl.openConnection();

		conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		conn.setRequestProperty("Connection", "Keep-Alive");

		if (sendCookie != null && sendCookie != "")
			conn.setRequestProperty("Cookie", sendCookie);
		if (sendMethod.equals("post"))
			sendPost();

		try {
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), sendCodeing));// sbo.getSendCodeing()
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return result;
	}
	/**
	 * 获取市场利率网站连接及数据
	 * @param type 0-多日期单曲线，需赋值 StrstartDate ,StrendDate ,item；<br/>1-多曲线单日期，需赋值queryDate,items
	 * */
	public  ArrayCollection getAmfContent(String type) {
		DoAmf doamf=new DoAmf();
		AMFConnection connection = new AMFConnection();
		ArrayCollection yc=null;
		try {
			connection.connect("http://yield.chinabond.com.cn/cbweb-mn/messagebroker/amf");
			RemotingMessage rm=null;
			if(type.equals("1")){
				rm=doamf.buildRequestByItems(queryDate,items);
			}else if(type.equals("0")){
				rm=doamf.buildRequestByDate(StrstartDate ,StrendDate ,item);
			}else{
				rm=doamf.buildRequestByDate(StrstartDate,StrendDate ,item);
			}
			//Object test=connection.call(null, rm);
			AcknowledgeMessage msg=(AcknowledgeMessage) connection.call(null, rm);
			yc=(ArrayCollection) msg.getBody();
			YcChartData[] ycDatas=new YcChartData[yc.size()];
			for(int i=0;i<yc.size();i++){
				ycDatas[i]=(YcChartData) yc.get(i);
			}
			
		} catch (ClientStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return yc;
	}

	public URL getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String urlAddr) {
		try {
			this.sendUrl = new URL(urlAddr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSendParm() {
		return sendParm;
	}

	public void setSendParm(String sendParm) {
		this.sendParm = sendParm;
	}

	public String getSendCookie() {
		return sendCookie;
	}

	public void setSendCookie(String sendCookie) {
		this.sendCookie = sendCookie;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public String getSendCodeing() {
		return sendCodeing;
	}

	public void setSendCodeing(String sendCodeing) {
		this.sendCodeing = sendCodeing;
	}

	public  String[] getItems() {
		return items;
	}

	public  void setItems(String[] items) {
		this.items = items;
	}

	public  String getItem() {
		return item;
	}

	public  void setItem(String item) {
		this.item = item;
	}

	public  String getQueryDate() {
		return queryDate;
	}

	public  void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public  String getStrstartDate() {
		return StrstartDate;
	}

	public  void setStrstartDate(String strstartDate) {
		this.StrstartDate = strstartDate;
	}

	public  String getStrendDate() {
		return StrendDate;
	}

	public  void setStrendDate(String strendDate) {
		this.StrendDate = strendDate;
	}

	public  void setSendUrl(URL sendUrl) {
		this.sendUrl = sendUrl;
	}

}