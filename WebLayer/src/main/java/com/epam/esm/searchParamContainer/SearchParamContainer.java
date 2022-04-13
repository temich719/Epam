package com.epam.esm.searchParamContainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SearchParamContainer {

    @NotNull(message = "Please input tag name")
    @NotEmpty(message = "Name should not be empty")
    String tagName;

    String namePart;
    String descriptionPart;

    @Pattern(regexp = "asc|desc", message = "Sort can be executed only with acs or desc params")
    String sortByName;

    @Pattern(regexp = "asc|desc", message = "Sort can be executed only with acs or desc params")
    String sortByDate;

    public Map<String, String> getMapOfSearchParams() {
        Map<String, String> mapOfSearchParams = new HashMap<>();
        mapOfSearchParams.put("tagName", tagName);
        mapOfSearchParams.put("namePart", namePart);
        mapOfSearchParams.put("descriptionPart", descriptionPart);
        mapOfSearchParams.put("sortByName", sortByName);
        mapOfSearchParams.put("sortByDate", sortByDate);
        return mapOfSearchParams;
    }
}
