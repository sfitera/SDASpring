package org.dreamteam.sda.controller.web.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CreateInvoiceItem {
        String productId;
        int amount;
}
