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
public class CountryResource {
    @Autowired
    private ICountryInfoService countryInfoService;

    @GetMapping("/countries/{code}")
    public ResponseEntity<CountryInfoDTO> getCountryInfoByCode(@PathVariable("code") @Pattern(regexp = "^[a-zA-Z]{2}$") String countryCode) {

        return ResponseEntity.ok(countryInfoService.getCountryInfoByCode(countryCode.toUpperCase()));
    }

}
