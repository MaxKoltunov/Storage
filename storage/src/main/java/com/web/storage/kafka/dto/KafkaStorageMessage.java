package com.web.storage.kafka.dto;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaStorageMessage {

    private String name;

    private String type;

    private String brand;

    private Long cost;

    private Timestamp arrivalDate;

    private Long discountId;

    private Long amount;
}
