package org.dreamteam.sda.controller.rest.request;

import lombok.NonNull;
import java.time.LocalDate;

public record CreateInvoice(@NonNull String clientId, @NonNull LocalDate date ) {


}
