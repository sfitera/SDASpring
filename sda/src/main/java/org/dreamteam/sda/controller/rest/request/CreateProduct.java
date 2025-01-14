package org.dreamteam.sda.controller.rest.request;

import lombok.NonNull;

public record CreateProduct(@NonNull String name, @NonNull String price) {


}
