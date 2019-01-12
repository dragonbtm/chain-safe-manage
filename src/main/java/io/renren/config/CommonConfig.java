package io.renren.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.Valid;

//@Configuration
//@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
public class CommonConfig {

	public static String NODENO = "ps13452aw";

	public static int SLEEP_TIME = 1000;

	public static String toViewString() {
		return "\r\nNODENO[" + NODENO + "]\r\n";
	}

	//websocket链接信息
	@Valid()
	public static String wssUrl;


}
