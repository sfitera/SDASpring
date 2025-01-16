package org.dreamteam.sda.controller.rest.request;

import lombok.NonNull;

public record CreateInvoiceItem(@NonNull String productId,
                                int amount ) {


}
