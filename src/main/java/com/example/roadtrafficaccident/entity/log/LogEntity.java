package com.example.roadtrafficaccident.entity.log;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogEntity {

    @Id
    @GeneratedValue(generator = "uuid_gen")
    @GenericGenerator(name = "uuid_gen", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String method;

    private LocalDateTime timeStamp;

    private Long executionTime;

    private String result;

    private String exception;

    private String clientHost;

}
