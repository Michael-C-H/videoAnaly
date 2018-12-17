package com.kingwant.videoAnaly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
 
/**
 * @author zh
 * @ClassName cn.saytime.Swgger2
 * @Description
 * @date 2017-07-10 22:12:31
 */
@Configuration
public class Swagger2 {
 
	@Bean
	public Docket createRestApi() {		

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo("全部"))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.kingwant.videoAnaly.controller"))
				.paths(PathSelectors.any())
				.build();
				//.globalOperationParameters(addHeader());
	}
	
	/*@Bean
	public Docket appApi() {		

		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/app/**"))
                .build().groupName("APP接口文档")
                .pathMapping("/")
                .apiInfo(apiInfo("APP"))
				.globalOperationParameters(addHeader());
	}
	
	@Bean
	public Docket xcxApi() {		

		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/xcx/**"))
                .build().groupName("小程序接口文档")
                .pathMapping("/")
                .apiInfo(apiInfo("小程序"))
				.globalOperationParameters(addHeader());
	}
	
	
	@Bean
	public Docket pcApi() {		

		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/pc/**"))
                .build().groupName("PC接口文档")
                .pathMapping("/")
                .apiInfo(apiInfo("PC"))
				.globalOperationParameters(addHeader());
	}*/
	
	
	private ApiInfo apiInfo(String name) {
		return new ApiInfoBuilder()
				.title("视频分析接口文档-"+name)
				.description("简单优雅的restful风格")
				.termsOfServiceUrl("https://www.baidu.com/")
				.version("1.0")
				.build();
	}
	
	/**
	 * 添加头参数
	 * @return
	 */
	/*private List<Parameter> addHeader(){
		ParameterBuilder tokenPar = new ParameterBuilder();
    	List<Parameter> pars = new ArrayList<Parameter>();
    	tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	pars.add(tokenPar.build());
    	
    	return pars;
	}*/
}