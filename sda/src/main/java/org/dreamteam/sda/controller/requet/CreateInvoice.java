package org.dreamteam.sda.controller.requet;

import lombok.NonNull;
import org.dreamteam.sda.model.Client;

import java.sql.Date;
import java.time.LocalDate;

public record CreateInvoice(@NonNull Client client, @NonNull LocalDate date ) {


}
