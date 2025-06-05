package com.cgidemo.country_info_service.service.impl;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;
import com.cgidemo.country_info_service.service.ICountryInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CountryInfoServiceImplTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICountryInfoService countryInfoService;

    @Test
    void testGetCountryByCode_Success() throws Exception {
        CountryInfoDTO response = new CountryInfoDTO();
        response.setCountryCode("US");
        response.setName("United States of America");
        response.setCapital("Washington, D.C.");
        response.setRegion("Americas");
        response.setCurrencies(List.of("USD"));
        response.setLanguages(List.of("English"));
        response.setBorders(List.of("CAN", "MEX"));
        response.setSizeCategory("LARGE");

        Mockito.when(countryInfoService.getCountryInfoByCode("US")).thenReturn(response);

        mockMvc.perform(get("/api/v1/countries/US")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryCode").value("US"))
                .andExpect(jsonPath("$.name").value("United States of America"))
                .andExpect(jsonPath("$.capital").value("Washington, D.C."))
                .andExpect(jsonPath("$.region").value("Americas"))
                .andExpect(jsonPath("$.currencies[0]").value("USD"))
                .andExpect(jsonPath("$.languages[0]").value("English"))
                .andExpect(jsonPath("$.borders[0]").value("CAN"))
                .andExpect(jsonPath("$.borders[1]").value("MEX"))
                .andExpect(jsonPath("$.sizeCategory").value("LARGE"));
    }

    @Test
    void testGetCountryByCode_InvalidCode() throws Exception {
        mockMvc.perform(get("/api/v1/countries/US1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testGetCountryByCode_NotFound() throws Exception {
        Mockito.when(countryInfoService.getCountryInfoByCode("XX"))
                .thenThrow(new com.cgidemo.country_info_service.exception.ResourceNotFoundException("Country code not found"));

        mockMvc.perform(get("/api/v1/countries/XX")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}