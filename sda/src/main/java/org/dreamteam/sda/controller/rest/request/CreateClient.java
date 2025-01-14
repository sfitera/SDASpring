package org.dreamteam.sda.controller.rest.request;

import lombok.NonNull;

public record CreateClient(@NonNull String name, @NonNull String address ) {


}
