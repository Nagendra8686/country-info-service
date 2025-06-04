package com.cgidemo.country_info_service.service.impl;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;
import com.cgidemo.country_info_service.exception.ResourceNotFoundException;
import com.cgidemo.country_info_service.service.mappers.CountryInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryInfoServiceImplTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryInfoMapper countryInfoMapper;

    @InjectMocks
    private CountryInfoServiceImpl countryInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Returns country info for valid country code")
    void returnsCountryInfoForValidCountryCode() {
        String countryCode = "US";
        String url = "https://restcountries.com/v3.1/alpha/" + countryCode;
        Map<String, Object> mockResponse = Map.of("name", "United States");
        CountryInfoDTO mockDTO = new CountryInfoDTO();
        mockDTO.setName("United States");

        when(restTemplate.getForObject(url, List.class)).thenReturn(List.of(mockResponse));
        when(countryInfoMapper.mapToDTO(mockResponse)).thenReturn(mockDTO);

        CountryInfoDTO result = countryInfoService.getCountryInfoByCode(countryCode);

        assertNotNull(result);
        assertEquals("United States", result.getName());
        verify(restTemplate, times(1)).getForObject(url, List.class);
    }

    @Test
    @DisplayName("Throws ResourceNotFoundException for invalid country code")
    void throwsResourceNotFoundExceptionForInvalidCountryCode() {
        String countryCode = "INVALID";
        String url = "https://restcountries.com/v3.1/alpha/" + countryCode;

        when(restTemplate.getForObject(url, List.class)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> countryInfoService.getCountryInfoByCode(countryCode));
        verify(restTemplate, times(1)).getForObject(url, List.class);
    }

    @Test
    @DisplayName("Throws exception when API call fails")
    void throwsExceptionWhenApiCallFails() {
        String countryCode = "US";
        String url = "https://restcountries.com/v3.1/alpha/" + countryCode;

        when(restTemplate.getForObject(url, List.class)).thenThrow(new RuntimeException("API error"));

        assertThrows(RuntimeException.class, () -> countryInfoService.getCountryInfoByCode(countryCode));
        verify(restTemplate, times(1)).getForObject(url, List.class);
    }
}