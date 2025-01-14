package org.dreamteam.sda.controller.rest.request;

import java.time.LocalDate;

public record UpdateInvoice(String clientId, LocalDate date ) {

}
