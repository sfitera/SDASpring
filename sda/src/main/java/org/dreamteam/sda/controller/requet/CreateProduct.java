package org.dreamteam.sda.controller.requet;

import lombok.NonNull;

public record CreateProduct(@NonNull String name, @NonNull String price) {


}
