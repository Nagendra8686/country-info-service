package com.cgidemo.country_info_service.controller;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;
import com.cgidemo.country_info_service.service.ICountryInfoService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Validated
public class CountryResourceController {
    @Autowired
    private ICountryInfoService countryInfoService;

    /**
     * Handles HTTP GET requests to fetch country information by its ISO 2-letter country code.
     *
     * @param countryCode The ISO 2-letter country code provided as a path variable.
     *                    Must match the regex pattern "^[a-zA-Z]{2}$" to ensure valid input.
     * @return A ResponseEntity containing the `CountryInfoDTO` object with country details.
     * The response is returned with HTTP status 200 (OK) if successful.
     * @throws jakarta.validation.ConstraintViolationException If the provided country code does not match the required pattern.
     */
    @GetMapping("/countries/{code}")
    public ResponseEntity<CountryInfoDTO> getCountryInfoByCode(@PathVariable("code") @Pattern(regexp = "^[a-zA-Z]{2}$") String countryCode) {

        // Convert the country code to uppercase and fetch the country information using the service
        return ResponseEntity.ok(countryInfoService.getCountryInfoByCode(countryCode.toUpperCase()));
    }

}
