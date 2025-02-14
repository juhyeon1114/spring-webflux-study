package study.r2dbcmysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@EnableR2dbcAuditing
@SpringBootApplication
public class R2dbcUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcUserApplication.class, args);
    }

}
