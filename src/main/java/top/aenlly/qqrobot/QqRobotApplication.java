package top.aenlly.qqrobot;

import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@MapperScan(basePackages = "top.aenlly.qqrobot.mapper")
public class QqRobotApplication {

    public static void main(String[] args) {
        String home = System.getProperty("home");
        if(StrUtil.isBlank(home)){
            System.setProperty("home", QqRobotApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"../");
        }
        SpringApplication.run(QqRobotApplication.class, args);
    }

}
