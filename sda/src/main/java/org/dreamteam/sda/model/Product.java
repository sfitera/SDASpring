package org.dreamteam.sda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product{
    @Id
    private String id;
    private String name;
    private String price;
}
