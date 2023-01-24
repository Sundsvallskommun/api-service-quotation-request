package se.sundsvall.quotationrequest.integration.lime.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("integration.lime")
public record LimeProperties(int connectTimeout, int readTimeout, String apiKey) {
}
