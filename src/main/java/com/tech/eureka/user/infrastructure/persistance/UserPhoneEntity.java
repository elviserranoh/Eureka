package com.tech.eureka.user.infrastructure.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_PHONES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    private Integer cityCode;

    private Integer countryCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserPhoneEntity(Long number, Integer cityCode, Integer countryCode, UserEntity userEntity) {
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
        this.user = userEntity;
    }
}
