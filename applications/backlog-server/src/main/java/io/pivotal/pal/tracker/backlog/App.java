package io.pivotal.pal.tracker.backlog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestOperations;

import javax.sql.DataSource;
import java.util.Map;
import java.util.TimeZone;


@SpringBootApplication
@ComponentScan({"io.pivotal.pal.tracker.backlog", "io.pivotal.pal.tracker.restsupport"})
@EnableEurekaClient
public class App {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
        Map<String, DataSource> dataSourceMap = run.getBeansOfType(DataSource.class);
        dataSourceMap.forEach((s, dataSource) -> System.out.println(dataSource.toString() + " ::"+ s));

    }

    @Bean
    ProjectClient projectClient(
        RestOperations restOperations,
        @Value("${registration.server.endpoint}") String registrationEndpoint
    ) {
        return new ProjectClient(restOperations, registrationEndpoint);
    }
}
