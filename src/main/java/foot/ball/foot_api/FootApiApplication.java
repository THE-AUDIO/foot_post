package foot.ball.foot_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(FootApiApplication.class, args);
	}

}
