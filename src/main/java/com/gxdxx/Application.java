package com.gxdxx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 메인 클래스
@SpringBootApplication  // 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args); /* SpringApplication.run으로 인해 내장 WAS를 실행
                                                            (별도로 외부에 WAS를 두지 않고 애플리케이션을 실행할 때
                                                             내부에서 WAS를 실행해서 서버에 톰캣을 설치할 필요가
                                                             없게되고 Jar 파일로 실행하면 됨
                                                             => 언제 어디서나 같은 환경에서 스프링 부트를 배포 가능 */
    }
}
