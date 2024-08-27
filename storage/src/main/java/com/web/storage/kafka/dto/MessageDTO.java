package com.web.storage.kafka.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {

    private Long id;

    private Long cost;

    private Timestamp arrivalDate;

    private Long discountId;

    private Long amount;
}
