package com.tech.eureka.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone implements Serializable {
    private Long id;
    private Long number;
    private Integer cityCode;
    private Integer countryCode;
    @JsonIgnore
    private User userId;

    public Phone(Long id, Long number, Integer cityCode, Integer countryCode) {
        this.id = id;
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }
}
