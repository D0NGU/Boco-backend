package ntnu.idatt.boco.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    /**
     * Inject Server Endpoint Exporter,
     * This bean automatically registers Web socket endpoint using the @ServerEndpoint annotation declaration
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}