package org.dreamteam.sda.controller.web.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.dreamteam.sda.model.Client;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode
@Builder
public class UpdateInvoice {
        @NonNull String id;
        @NotBlank(message = "Client is required")
        Client client;
        @NotNull(message = "Date is required")
        LocalDate date;


}
