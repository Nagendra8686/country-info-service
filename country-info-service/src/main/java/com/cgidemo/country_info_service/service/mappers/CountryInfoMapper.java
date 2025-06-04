package com.cgidemo.country_info_service.service.mappers;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CountryInfoMapper {


    public static CountryInfoDTO mapToDTO(Map<String,Object> apiResponse) {
        CountryInfoDTO countryInfo = new CountryInfoDTO();
        countryInfo.setCountryCode((String) apiResponse.get("cca2"));
        Map<String, Object> nameMap = (Map<String, Object>) apiResponse.get("name");
        countryInfo.setName(nameMap != null ? (String) nameMap.getOrDefault("official", "") : "");

        List<String> capitals = (List<String>) apiResponse.get("capital");
        countryInfo.setCapital((capitals != null && !capitals.isEmpty()) ? capitals.get(0) : "");

        countryInfo.setRegion((String) apiResponse.get("region"));
        // Currencies
        Map<String, Object> currenciesMap = (Map<String, Object>) apiResponse.get("currencies");
        List<String> currencies = new ArrayList<>();
        if (currenciesMap != null) {
            currencies.addAll(currenciesMap.keySet());
        }
        countryInfo.setCurrencies(currencies);
        // Languages
        Map<String, Object> languagesMap = (Map<String, Object>) apiResponse.get("languages");
        List<String> languages = new ArrayList<>();
        if (languagesMap != null) {
            languages.addAll(languagesMap.values().stream().map(Object::toString).toList());
        }
        countryInfo.setLanguages(languages);
        // Borders
        List<String> borders = (List<String>) apiResponse.get("borders");
        countryInfo.setBorders(borders != null ? borders : new ArrayList<>());
        Number population = (Number) apiResponse.getOrDefault("population", 0);

        countryInfo.setSizeCategory(determineSizeCategory(population.longValue()));
        return countryInfo;

    }

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
