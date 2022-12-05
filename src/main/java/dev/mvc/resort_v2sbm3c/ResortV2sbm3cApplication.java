package dev.mvc.resort_v2sbm3c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//★★★★★★ 패키지 주의 ★★★★★★, dev.mvc로 시작하는 모든 패키지의 class 자동 생성 
@ComponentScan(basePackages = {"dev.mvc"}) 
public class ResortV2sbm3cApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResortV2sbm3cApplication.class, args);
	}

}
