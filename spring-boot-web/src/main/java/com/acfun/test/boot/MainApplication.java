/**
 * 文件名：@MainApplication.java <br/>
 * 包名：tv.acfun.subtle.boot <br/>
 * 项目名：acfun-subtle <br/>
 * @author xtwin <br/>
 */
package com.acfun.test.boot;

import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.Ks3ClientConfig.PROTOCOL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

/**
 * 类名：MainApplication  <br />
 *
 * 功能：主类
 *
 * @author xtwin <br />
 * 创建时间：2016年5月13日 下午2:19:41  <br />
 * @version 2016年5月13日
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.acfun.test")
@ConfigurationProperties(prefix = "ks")
//@EnableScheduling
public class MainApplication {
	
	@Value("${ks.access.key}")
	private String accessKey;
	
	@Value("${ks.secret.key}")
	private String secretKey;
	
	/**
	 * 功能：上传文件解析器 <br/>
	 *
	 * @author xtwin <br/>
	 * @version 2016年6月17日 下午5:04:35 <br/>
	 */
//	@Bean(name = "multipartResolver")
//	public MultipartResolver multipartResolver() {
//		CommonsMultipartResolver mpr = new CommonsMultipartResolver();
//		
//		mpr.setMaxUploadSize(4 * 1024 * 1024 * 1024);
//		//mpr.setMaxInMemorySize(4096);
//		mpr.setDefaultEncoding("UTF-8");
//		
//		return mpr;
//	}
	
	/**
	 * 功能：金山客户端 <br/>
	 *
	 * @author xtwin <br/>
	 * @version 2016年6月17日 上午11:31:08 <br/>
	 */
	@Bean
	public Ks3 ks3Client() {
		// http://ks3.ksyun.com/doc/sdk/java.html
		// 1.初始化：配置参数
		Ks3ClientConfig config = new Ks3ClientConfig();
		/**
		 * 设置服务地址</br>
		 * 中国（北京）| ks3-cn-beijing.ksyun.com
		 * 中国（上海）| ks3-cn-shanghai.ksyun.com
		 * 中国（香港）| ks3-cn-hk-1.ksyun.com
		 * 美国（圣克拉拉）| ks3-us-west-1.ksyun.com
		*/
		config.setEndpoint("ks3-cn-beijing.ksyun.com");   //此处以北京region为例
		config.setProtocol(PROTOCOL.http);
		/**
		*true表示以   endpoint/{bucket}/{key}的方式访问</br>
		*false表示以  {bucket}.endpoint/{key}的方式访问
		*/
		config.setPathStyleAccess(false);
		
		HttpClientConfig hconfig = new HttpClientConfig();
		//在HttpClientConfig中可以设置httpclient的相关属性，比如代理，超时，重试等。

		config.setHttpClientConfig(hconfig);
		
		// 初始化客户端
		return new Ks3Client(accessKey, secretKey, config);
	}
	
	/**
	 * 功能：构建金山云的转码规则 <br/>
	 *
	 * @author xtwin <br/>
	 * @version 2016年6月17日 上午11:03:52 <br/>
	 */
	@Bean(name = "encodeMap")
	public Map<String, String> encodeMap() {
		return transcoding;
	}
	
	/**
	 * 功能：启动 <br/>
	 *
	 * @author xtwin <br/>
	 * @version 2016年6月17日 上午11:05:29 <br/>
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	/**
	 * @version 2016年6月17日-上午11:05:23
	 */
	public Map<String, String> getTranscoding() {
		return transcoding;
	}

	/**
	 * @version 2016年6月17日-上午11:05:23
	 */
	public void setTranscoding(Map<String, String> transcoding) {
		this.transcoding = transcoding;
	}
	
	/**
	 *  该字段配合@ConfigurationProperties(prefix = "ks")使用
	 */
	private Map<String, String> transcoding;
}