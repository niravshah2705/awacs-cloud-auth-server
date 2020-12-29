package com.aiocdwacs.awacscloudauthserver.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiocdwacs.awacscloudauthserver.service.EmailService;

//https://csp.withgoogle.com/docs/index.html

//curl --location --request GET 'http://localhost:8100/actuator/csp/report' \
//--header 'Content-Type: application/json' \
//--data-raw '{
//   "csp-report":{
//      "document-uri":"https://example.com/foo/bar",
//      "referrer":"https://www.google.com/",
//      "violated-directive":"default-src self",
//      "original-policy":"default-src self; report-uri /csp-hotline.php",
//      "blocked-uri":"http://evilhackerscripts.com"
//   }
//}'

@Component
@RestControllerEndpoint(id="csp")
public class RestCspReportCustomEndpoint {

	@Autowired
	EmailService emailService;
	
	Logger logger = LoggerFactory.getLogger(RestCspReportCustomEndpoint.class);
	
	@Retryable(maxAttempts=3, value=RuntimeException.class, backoff = @Backoff( delay = 300000, multiplier = 2) )
	@GetMapping("/report")
    public @ResponseBody ResponseEntity<String> reportEndpoint(@RequestBody CspReport incident){
		
		logger.warn("ALERT ALERT ALERT");
		logger.warn("CSP Incident detected - "+ incident);

		// send notification ??
		return ResponseEntity.ok("incident acknowledged!");
    }
}
