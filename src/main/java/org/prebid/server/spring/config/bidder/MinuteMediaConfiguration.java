package org.prebid.server.spring.config.bidder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.prebid.server.bidder.BidderDeps;
import org.prebid.server.bidder.minutemedia.MinuteMediaBidder;
import org.prebid.server.json.JacksonMapper;
import org.prebid.server.spring.config.bidder.model.BidderConfigurationProperties;
import org.prebid.server.spring.config.bidder.util.BidderDepsAssembler;
import org.prebid.server.spring.config.bidder.util.UsersyncerCreator;
import org.prebid.server.spring.env.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import jakarta.validation.constraints.NotBlank;

@Configuration
@PropertySource(value = "classpath:/bidder-config/minutemedia.yaml", factory = YamlPropertySourceFactory.class)
public class MinuteMediaConfiguration {

    private static final String BIDDER_NAME = "minutemedia";

    @Bean("minutemediaConfigurationProperties")
    @ConfigurationProperties("adapters.minutemedia")
    MinuteMediaConfigurationProperties configurationProperties() {
        return new MinuteMediaConfigurationProperties();
    }

    @Bean
    BidderDeps minutemediaBidderDeps(MinuteMediaConfigurationProperties minutemediaConfigurationProperties,
                                     @NotBlank @Value("${external-url}") String externalUrl,
                                     JacksonMapper mapper) {

        return BidderDepsAssembler.<MinuteMediaConfigurationProperties>forBidder(BIDDER_NAME)
                .withConfig(minutemediaConfigurationProperties)
                .usersyncerCreator(UsersyncerCreator.create(externalUrl))
                .bidderCreator(config -> new MinuteMediaBidder(
                        config.getEndpoint(),
                        config.getTestEndpoint(),
                        mapper))
                .assemble();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    private static class MinuteMediaConfigurationProperties extends BidderConfigurationProperties {

        private String testEndpoint;
    }
}
