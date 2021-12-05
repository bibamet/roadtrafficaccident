package com.example.roadtrafficaccident.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {

    @Id
    @GeneratedValue(generator = "uuid_gen")
    @GenericGenerator(name = "uuid_gen", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private Double longtitude;

    private Double latitude;

    private String addressView;

}
