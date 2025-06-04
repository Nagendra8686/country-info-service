package com.cgidemo.country_info_service.service;

import com.cgidemo.country_info_service.dto.CountryInfoDTO;

public interface ICountryInfoService {

    public CountryInfoDTO getCountryInfoByCode(String countryCode);
}
