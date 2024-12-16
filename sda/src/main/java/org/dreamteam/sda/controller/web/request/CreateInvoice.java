package org.dreamteam.sda.controller.web.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dreamteam.sda.model.Client;


@Data
@EqualsAndHashCode
@Builder
public class CreateInvoice {
        @NotNull(message = "Client is required")
        Client client;
        @NotBlank(message = "Date is required")
        String date;


}
