package com.cgidemo.country_info_service.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String countryCode;
    private String name;
    private String capital;
    private String region;
    private List<String> currencies;
    private List<String> languages;
    private List<String> borders;
    private String sizeCategory;
}
