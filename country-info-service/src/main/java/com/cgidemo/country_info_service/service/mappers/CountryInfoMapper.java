package com.cgidemo.country_info_service.service.mappers;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CountryInfoMapper {


    /**
     * Maps the API response to a `CountryInfoDTO` object.
     *
     * @param apiResponse A map containing the API response data. Keys represent field names, and values represent field values.
     *                    Expected keys include:
     *                    - "cca2": ISO 2-letter country code.
     *                    - "name": A map containing country name details, including "official".
     *                    - "capital": A list of capital city names.
     *                    - "region": The region of the country.
     *                    - "currencies": A map of currency codes.
     *                    - "languages": A map of language codes and names.
     *                    - "borders": A list of bordering country codes.
     *                    - "population": The population of the country.
     * @return A `CountryInfoDTO` object populated with the mapped data.
     */
    public static CountryInfoDTO mapToDTO(Map<String, Object> apiResponse) {
        CountryInfoDTO countryInfo = new CountryInfoDTO();

        // Map the ISO 2-letter country code
        countryInfo.setCountryCode((String) apiResponse.get("cca2"));

        // Map the official name of the country
        Map<String, Object> nameMap = (Map<String, Object>) apiResponse.get("name");
        countryInfo.setName(nameMap != null ? (String) nameMap.getOrDefault("official", "") : "");

        // Map the capital city (first entry in the list, if available)
        List<String> capitals = (List<String>) apiResponse.get("capital");
        countryInfo.setCapital((capitals != null && !capitals.isEmpty()) ? capitals.get(0) : "");

        // Map the region
        countryInfo.setRegion((String) apiResponse.get("region"));

        // Map the currencies (keys of the currencies map)
        Map<String, Object> currenciesMap = (Map<String, Object>) apiResponse.get("currencies");
        List<String> currencies = new ArrayList<>();
        if (currenciesMap != null) {
            currencies.addAll(currenciesMap.keySet());
        }
        countryInfo.setCurrencies(currencies);

        // Map the languages (values of the languages map)
        Map<String, Object> languagesMap = (Map<String, Object>) apiResponse.get("languages");
        List<String> languages = new ArrayList<>();
        if (languagesMap != null) {
            languages.addAll(languagesMap.values().stream().map(Object::toString).toList());
        }
        countryInfo.setLanguages(languages);

        // Map the bordering countries
        List<String> borders = (List<String>) apiResponse.get("borders");
        countryInfo.setBorders(borders != null ? borders : new ArrayList<>());

        // Map the population and determine the size category
        Number population = (Number) apiResponse.getOrDefault("population", 0);
        countryInfo.setSizeCategory(determineSizeCategory(population.longValue()));

        return countryInfo;
    }

    /**
     * Determines the size category of a country based on its population.
     *
     * @param population The population of the country as a `Long`.
     *                   - If the population is less than 1,000,000, the size category is "SMALL".
     *                   - If the population is between 1,000,000 and 10,000,000 (inclusive), the size category is "MEDIUM".
     *                   - If the population is greater than 10,000,000, the size category is "LARGE".
     * @return A `String` representing the size category ("SMALL", "MEDIUM", or "LARGE").
     */
    private static String determineSizeCategory(Long population) {
        if (population < 1000000) {
            return "SMALL";
        } else if (population <= 10000000) {
            return "MEDIUM";
        } else {
            return "LARGE";
        }
    }

}
