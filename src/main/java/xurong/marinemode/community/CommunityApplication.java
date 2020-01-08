package xurong.marinemode.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 这个东西会自动扫描Application的同级和下级目录, 把所有的bean存到容器中 */
@SpringBootApplication
@MapperScan("xurong.marinemode.community.mapper")
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
