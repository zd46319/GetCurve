package com.cb.common.biz.yield.domain;

import flex.messaging.io.ArrayCollection;

public class YcChartData {
	private String ycDefName;
	private Boolean isPoint;
	private String ycDefId;
	private String worktime;
	private ArrayCollection seriesData;
	public String getYcDefName() {
		return ycDefName;
	}
	public void setYcDefName(String ycDefName) {
		this.ycDefName = ycDefName;
	}
	public Boolean getIsPoint() {
		return isPoint;
	}
	public void setIsPoint(Boolean isPoint) {
		this.isPoint = isPoint;
	}
	public String getYcDefId() {
		return ycDefId;
	}
	public void setYcDefId(String ycDefId) {
		this.ycDefId = ycDefId;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public ArrayCollection getSeriesData() {
		return seriesData;
	}
	public void setSeriesData(ArrayCollection seriesData) {
		this.seriesData = seriesData;
	}
	

}
