package cn.itcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SpringbootMybatisBatchTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisBatchTestApplication.class, args);
	}

}
