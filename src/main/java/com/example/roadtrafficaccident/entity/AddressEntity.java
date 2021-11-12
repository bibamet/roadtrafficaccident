package com.example.roadtrafficaccident.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
//    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    @GeneratedValue(generator = "uuid_gen")
    @GenericGenerator(name = "uuid_gen", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private Double longtitude;

    private Double latitude;

    private String addressView;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
//    List<RTAEntity> rta;

}
