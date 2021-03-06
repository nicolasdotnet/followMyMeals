package net.nicolasdot.meal_service.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author nicolasdotnet
 */
@Configuration("swaggerConfigProperties")
public class SwaggerConfigProperties {

    @Value("${api.version}")
    private String apiVersion;

    @Value("${swagger.enabled}")
    private String enabled = "false";

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.default_produces_and_consumes}")
    private String producesAndconsumes;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getProducesAndconsumes() {
        return producesAndconsumes;
    }

    public void setProducesAndconsumes(String producesAndconsumes) {
        this.producesAndconsumes = producesAndconsumes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
