package com.cgidemo.country_info_service.service.impl;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;
import com.cgidemo.country_info_service.exception.ResourceNotFoundException;
import com.cgidemo.country_info_service.service.ICountryInfoService;
import com.cgidemo.country_info_service.service.mappers.CountryInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CountryInfoServiceImpl implements ICountryInfoService {

    private final RestTemplate restTemplate;

    private static final String API_URL = "https://restcountries.com/v3.1/alpha/";

    public CountryInfoServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Fetches country information by its ISO 2-letter country code.
     *
     * @param countryCode The ISO 2-letter country code to fetch information for.
     * @return A `CountryInfoDTO` object containing details about the country.
     * @throws ResourceNotFoundException If no country is found for the given code.
     * @throws Exception                 For any other errors during the API call or processing.
     */
    @Override
    public CountryInfoDTO getCountryInfoByCode(String countryCode) {
        try {
            // Log the country code being fetched
            System.out.println("Fetching country info for code: " + countryCode);

            // Construct the API URL using the provided country code
            String url = API_URL + countryCode;

            // Make a GET request to the REST Countries API and parse the response as a list of maps
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            // Check if the response is null or empty, and throw an exception if no data is found
            if (response == null || response.isEmpty()) {
                throw new ResourceNotFoundException("Country code not found");
            }

            // Map the first item in the response to a CountryInfoDTO object and return it
            return CountryInfoMapper.mapToDTO(response.get(0));

        } catch (Exception e) {
            // Log the exception (stack trace printed for debugging purposes)
            e.printStackTrace();

            // Rethrow the exception to propagate the error
            throw e;
        }
    }
}
