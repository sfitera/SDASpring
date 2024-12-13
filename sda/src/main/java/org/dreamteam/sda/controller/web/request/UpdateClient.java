package org.dreamteam.sda.controller.web.request;


import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UpdateClient(
        @NonNull String id,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Address is required") String address ) {


}
