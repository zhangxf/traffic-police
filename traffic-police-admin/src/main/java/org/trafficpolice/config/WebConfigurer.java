package org.trafficpolice.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.trafficpolice.filter.HttpRequestMDCFilter;
import org.trafficpolice.support.ExceptionResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {
	
	private static String[] allowedOrigins= {"*"};
	
	@Autowired
    private ExceptionResolver exceptionResolver;
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setCacheSeconds(30 * 60);
		return messageSource;
	}
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).defaultContentType(MediaType.APPLICATION_JSON);
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        converters.add(stringHttpMessageConverter);
        
    	FastJsonHttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig cfg = new FastJsonConfig();
//        cfg.setSerializeFilters(
//            //百进制金额转换
//    		new CentesimalAmountConverterFilter(),
//            // 日期时间转换
//    		new DateTimeConverterFilter(),
//            //脱敏显示
//    		new MaskOutputFilter()
//        );
        SerializerFeature[] serializerFeatures = new SerializerFeature[] {
                SerializerFeature.BrowserSecure,
                SerializerFeature.DisableCircularReferenceDetect
        };
        cfg.setSerializerFeatures(serializerFeatures);
        jsonHttpMessageConverter.setFastJsonConfig(cfg);
        // 添加编码
        ArrayList<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        jsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(jsonHttpMessageConverter);
    }
    
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    	exceptionResolvers.add(exceptionResolvers.size() - 1, exceptionResolver);
    }
    
	@Override
    public void addArgumentResolvers(List< HandlerMethodArgumentResolver > argumentResolvers) {
//		argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());//支持注解@AuthenticationPrincipal取当前登录用户
    }
	
    @Bean  
    public FilterRegistrationBean httpRequestMDCFilter() {
	       FilterRegistrationBean registration = new FilterRegistrationBean();
	       registration.setFilter(new HttpRequestMDCFilter());
	       registration.addUrlPatterns("/*");
	       registration.setName("httpRequestMDCFilter");
	       registration.setOrder(Integer.MIN_VALUE + 55);
	       return registration;
	}
    
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
        .allowedHeaders("*")
        .allowedOrigins(allowedOrigins);
	}
	
	@Value("#{'${allowedOrigins}'.split(',')}")
	public void setAllowedOrigins(String[] allowedOrigins) {
		WebConfigurer.allowedOrigins = allowedOrigins;
	}
}