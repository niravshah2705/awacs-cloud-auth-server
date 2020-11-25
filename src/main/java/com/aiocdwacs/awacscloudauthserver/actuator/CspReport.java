package com.aiocdwacs.awacscloudauthserver.actuator;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.GsonBuilder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CspReport {

	Map<String, String> cspReportMap;

	@JsonAnyGetter
	public Map<String, String> getCspReportMap() {
		return cspReportMap;
	}

	public void setCspReportMap(Map<String, String> cspReportMap) {
		this.cspReportMap = cspReportMap;
	}
	
	public String toString(){
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
