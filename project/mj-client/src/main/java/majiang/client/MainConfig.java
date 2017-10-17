package majiang.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zuoge85@gmail.com on 2016/12/7.
 */

@EnableAutoConfiguration
@ComponentScan
@ImportResource("classpath*:game/boss/dao/DaoContext.xml")
@ConfigurationProperties(prefix = "jdbc")
@Configuration
@Import({DateSourceConfig.class, WebConfig.class})
public class MainConfig{

}
