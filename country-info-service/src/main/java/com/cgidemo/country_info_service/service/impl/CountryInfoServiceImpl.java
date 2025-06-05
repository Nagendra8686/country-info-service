package com.cgidemo.country_info_service.service.impl;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;
import com.cgidemo.country_info_service.exception.ResourceNotFoundException;
import com.cgidemo.country_info_service.service.ICountryInfoService;
import com.cgidemo.country_info_service.service.mappers.CountryInfoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class CountryInfoServiceImpl implements ICountryInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CountryInfoServiceImpl.class);
    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CountryInfoServiceImpl(@Value("${external.api.restcountries.url}") String apiUrl) {
        this.restTemplate = new RestTemplate();
        this.apiUrl = apiUrl;
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
            logger.info("Fetching country info for code: {}", countryCode);

            String url = apiUrl + countryCode;

            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response == null || response.isEmpty()) {
                logger.warn("No country found for code: {}", countryCode);
                throw new ResourceNotFoundException("Country code not found");
            }

            logger.info("Country info fetched successfully for code: {}", countryCode);

            return CountryInfoMapper.mapToDTO(response.get(0));

        } catch (ResourceNotFoundException resourceNotFoundException) {
            throw resourceNotFoundException;
        } catch (Exception exception) {
            logger.error("Error fetching country info for code: {}", countryCode, exception);
            throw exception;
        }
    }
}
