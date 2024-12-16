package org.dreamteam.sda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    private String id;
    @ManyToOne
    private Client client;
    private LocalDate date;
}
