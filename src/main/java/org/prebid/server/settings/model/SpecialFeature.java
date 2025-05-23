package org.prebid.server.settings.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SpecialFeature {

    @JsonProperty(defaultValue = "true")
    Boolean enforce;

    @JsonAlias("vendor-exceptions")
    List<String> vendorExceptions;
}
