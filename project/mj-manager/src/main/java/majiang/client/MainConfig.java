package majiang.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zuoge85@gmail.com on 2016/12/7.
 */

@EnableAutoConfiguration
@ComponentScan(basePackages={"majiang.client.controller"})
@Configuration
public class MainConfig {

}
