package org.dreamteam.sda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {
    @Id
    private String id;
    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private Product product;
    private int amount;
}
