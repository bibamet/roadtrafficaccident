package com.example.roadtrafficaccident.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

@Entity(name = "rta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RTAEntity {

    @Id
    @GeneratedValue(generator = "uuid_gen")
    @GenericGenerator(name = "uuid_gen", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true)
    private Long numberOfRta;

    @Column(unique = true)
    private String numberOfCar;

    private Long serialLicense;

    private Long numberLicense;

    LocalDateTime timeOfAccident;

    private Boolean guilty;

    private Double penalty;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

}
