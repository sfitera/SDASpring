package org.dreamteam.sda.controller.web.request;

import lombok.NonNull;

import java.time.LocalDate;

public record UpdateInvoice (@NonNull String invoiceId, String clientId, LocalDate date){
}


