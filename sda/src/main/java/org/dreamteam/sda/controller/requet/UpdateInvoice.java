package org.dreamteam.sda.controller.requet;

import org.dreamteam.sda.model.Client;
import java.util.Date;

public record UpdateInvoice(Client client, Date date ) {

}
