package org.dreamteam.sda.controller.web.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CreateInvoice {
        String clientID;
        LocalDate date;


}
