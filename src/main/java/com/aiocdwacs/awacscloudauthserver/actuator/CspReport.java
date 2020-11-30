

package com.aiocdwacs.awacscloudauthserver.actuator;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
