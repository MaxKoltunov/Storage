package com.web.storage.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "storage", schema = "storageschema")
public class StorageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_seq_gen")
    @SequenceGenerator(name = "storage_seq_gen", sequenceName = "storageschema.storage_id_seq", allocationSize = 1)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "recCost", nullable = false)
    private Long recCost;
}
