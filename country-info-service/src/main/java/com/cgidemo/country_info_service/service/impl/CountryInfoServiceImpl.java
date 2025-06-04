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

    @Override
    public CountryInfoDTO getCountryInfoByCode(String countryCode) {
        try {
            System.out.println("Fetching country info for code: " + countryCode);
            String url = API_URL + countryCode;
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
            if (response == null || response.isEmpty()) {
                throw new ResourceNotFoundException("Country code not found"); // No country found for the given code
            }
            return CountryInfoMapper.mapToDTO(response.get(0));

        } catch (Exception e) {
            // Log the exception (not shown here for brevity)
            e.printStackTrace();
            throw e;
        }

    }
}
