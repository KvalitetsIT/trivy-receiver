package dk.kvalitetsit.hello.integrationtest;

import com.github.dockerjava.api.model.VolumesFrom;
import dk.kvalitetsit.hello.VideoLinkHandlerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Collections;

public class ServiceStarter {
    private static final Logger logger = LoggerFactory.getLogger(ServiceStarter.class);
    private static final Logger serviceLogger = LoggerFactory.getLogger("trivy-receiver");
    private static final Logger mysqlLogger = LoggerFactory.getLogger("mysql");

    private Network dockerNetwork;
    private String jdbcUrl;

    public void startServices() {
        dockerNetwork = Network.newNetwork();

        setupDatabaseContainer();

        System.setProperty("JDBC.URL", jdbcUrl);
        System.setProperty("JDBC.USER", "hellouser");
        System.setProperty("JDBC.PASS", "secret1234");
        System.setProperty("KEY", "KEY");

        SpringApplication.run((VideoLinkHandlerApplication.class));
    }

    public GenericContainer startServicesInDocker() {
        dockerNetwork = Network.newNetwork();

        setupDatabaseContainer();

        var resourcesContainerName = "trivy-receiver-resources";
        var resourcesRunning = containerRunning(resourcesContainerName);
        logger.info("Resource container is running: " + resourcesRunning);

        GenericContainer service;

        // Start service
        if (resourcesRunning) {
            VolumesFrom volumesFrom = new VolumesFrom(resourcesContainerName);
            service = new GenericContainer<>("local/trivy-receiver-qa:dev")
                    .withCreateContainerCmdModifier(modifier -> modifier.withVolumesFrom(volumesFrom))
                    .withEnv("JVM_OPTS", "-javaagent:/jacoco/jacocoagent.jar=output=file,destfile=/jacoco-report/jacoco-it.exec,dumponexit=true,append=true -cp integrationtest.jar");
        } else {
            service = new GenericContainer<>("local/trivy-receiver-qa:dev")
                    .withFileSystemBind("/tmp", "/jacoco-report/")
                    .withEnv("JVM_OPTS", "-javaagent:/jacoco/jacocoagent.jar=output=file,destfile=/jacoco-report/jacoco-it.exec,dumponexit=true -cp integrationtest.jar");
        }

        service.withNetwork(dockerNetwork)
                .withNetworkAliases("trivy-receiver")

                .withEnv("LOG_LEVEL", "INFO")

                .withEnv("JDBC_URL", "jdbc:mysql://mysql:3306/hellodb")
                .withEnv("JDBC_USER", "hellouser")
                .withEnv("JDBC_PASS", "secret1234")
                .withEnv("KEY", "KEY")

                .withEnv("spring.flyway.locations", "classpath:db/migration,filesystem:/app/sql")

//                .withEnv("JVM_OPTS", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000")

                .withExposedPorts(8081,8080)
                .waitingFor(Wait.forHttp("/actuator").forPort(8081).forStatusCode(200));
        service.start();
        attachLogger(serviceLogger, service);

        return service;
    }

    private boolean containerRunning(String containerName) {
        return DockerClientFactory
                .instance()
                .client()
                .listContainersCmd()
                .withNameFilter(Collections.singleton(containerName))
                .exec()
                .size() != 0;
    }

    private void setupDatabaseContainer() {
        // Database server for Organisation.
        MySQLContainer mysql = (MySQLContainer) new MySQLContainer("mysql:5.7")
                .withDatabaseName("hellodb")
                .withUsername("hellouser")
                .withPassword("secret1234")
                .withNetwork(dockerNetwork)
                .withNetworkAliases("mysql");
        mysql.start();
        jdbcUrl = mysql.getJdbcUrl();
        attachLogger(mysqlLogger, mysql);
    }

    private void attachLogger(Logger logger, GenericContainer container) {
        ServiceStarter.logger.info("Attaching logger to container: " + container.getContainerInfo().getName());
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger);
        container.followOutput(logConsumer);
    }
}
