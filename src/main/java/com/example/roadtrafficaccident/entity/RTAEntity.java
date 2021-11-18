package com.example.roadtrafficaccident.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "rta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RTAEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_seq")
//    @SequenceGenerator(name = "rta_seq", sequenceName = "rta_seq", allocationSize = 1)
    @GeneratedValue(generator = "uuid_gen")
    @GenericGenerator(name = "uuid_gen", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true)
    private Long numberOfRta;

    @Column(unique = true)
    private String numberOfCar;

    private Long serial_license;

    private Long number_license;

    LocalDateTime timeOfAccident;

    private Boolean guilty;

    private Double penalty;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    AddressEntity address;

}
