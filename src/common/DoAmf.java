package common;

import java.util.HashMap;
import java.util.Map;

import flex.messaging.io.ArrayCollection;
import flex.messaging.messages.RemotingMessage;


public class DoAmf {
	private  Map<String,String>  CurveIdMap=new HashMap<String,String>(){{
		//request-content-body-obj[6][0]
		put("zcxjrz","8a8b2ca037a7ca910137bfaa94fa5057");//银行间固定利率政策性金融债(国开行)(到期)/
		put("gz","2c9081e50a2f9606010a3068cae70001"); //银行间固定利率国债(到期)/
		put("yp","2c9081e50a2f9606010a306b47e20005"); //央票收益率曲线/
		put("ptzAA-","2c9081e9259b766a0125bed2ab9d1599"); //银行间固定利率商业银行普通债(AA-)(到期)/
		}};
	
	/**
	 * 获取传入条件，构建amf请求，多曲线单日期
	 * @param queryDate 日期，格式为yyyymmdd
	 * @param String[] items 获取数据类型 zcxjrz：政策性金融债，gz：国债，ptzAA-:AA-银行普通债
	 * */
	public RemotingMessage buildRequestByItems(String queryDate,String[] items){
		RemotingMessage rm=new RemotingMessage();
		rm.setOperation("searchYc");
		rm.setSource(null);
		//rm.setHeader("DSID", "BBA3443B-1080-023B-0466-E8FB3BBB2BCB");
		//rm.setHeader("DSEndpoint", "my-amf");
		rm.setDestination("inityc");
		Object[] objs=new Object[7];
		String objs0="txy";
		objs[0]=objs0;
		objs[1]=new Object[]{queryDate.substring(0,4)+"-"+queryDate.substring(4,6)+queryDate.substring(6,8)};
		objs[2]=0;
		ArrayCollection ac_objs_3=new ArrayCollection();
		ac_objs_3.add("0");
		objs[3]=ac_objs_3;
		objs[4]="N";
		objs[5]="K";
		ArrayCollection ac_objs_6=new ArrayCollection();
		if(items!=null && items.length>0){
			for(int i=0;i<items.length;i++){
				if(CurveIdMap.containsKey(items[i])){
					ac_objs_6.add(CurveIdMap.get(items[i]));
				}
				
			}
		}
		objs[6]=ac_objs_6;
		Object bodyValue=(Object)objs;
		rm.setBody(bodyValue);
		rm.setClientId("BBA4291C-D0F0-DD3A-EC22-56A923BE633C");
		rm.setMessageId("2F48D148-CBD7-00A8-AB04-A9770716F986");
		rm.setTimeToLive(0);
		rm.setTimestamp(0);
		return rm;
	}
	
	/**
	 * 获取传入条件，构建amf请求，单曲线多日期
	 * @param StrstartDate 日期，格式为yyyymmdd
	 * @param StrendDate 日期，格式为yyyymmdd
	 * @param String item 获取数据类型 zcxjrz：政策性金融债，gz：国债，ptzAA-:AA-银行普通债,yp:央票
	 * */
	public RemotingMessage buildRequestByDate(String StrstartDate,String StrendDate,String item){
		RemotingMessage rm=new RemotingMessage();
		rm.setOperation("searchYc");
		rm.setSource(null);
		//rm.setHeader("DSID", "BBA3443B-1080-023B-0466-E8FB3BBB2BCB");
		//rm.setHeader("DSEndpoint", "my-amf");
		rm.setDestination("inityc");
		Object[] objs=new Object[7];
		String objs0="txym";
		objs[0]=objs0;
		long startDate = Long.valueOf(StrstartDate);
		long endDate = Long.valueOf(StrendDate);
		int int_loop = CommonFunctions.daysSubtract(endDate,startDate)+1;
		Object[] objs1=new Object[int_loop];
		for (int i = 0; i < int_loop; i++) {
			String newTodayDate = String.valueOf(CommonFunctions.pub_base_deadlineD(startDate, i));
			objs1[i]=newTodayDate.substring(0,4)+"-"+newTodayDate.substring(4,6)+"-"+newTodayDate.substring(6,8);
		}
		objs[1]=objs1;
		objs[2]=0;
		ArrayCollection ac_objs_3=new ArrayCollection();
		ac_objs_3.add("0");
		objs[3]=ac_objs_3;
		objs[4]="N";
		objs[5]="K";
		ArrayCollection ac_objs_6=new ArrayCollection();
		if(item!=null && !item.isEmpty()){
			if(CurveIdMap.containsKey(item)){
				ac_objs_6.add(CurveIdMap.get(item));
			}
				
		}
		objs[6]=ac_objs_6;
		Object bodyValue=(Object)objs;
		rm.setBody(bodyValue);
		rm.setClientId("BBA4291C-D0F0-DD3A-EC22-56A923BE633C");
		rm.setMessageId("2F48D148-CBD7-00A8-AB04-A9770716F986");
		rm.setTimeToLive(0);
		rm.setTimestamp(0);
		return rm;
	}
}
