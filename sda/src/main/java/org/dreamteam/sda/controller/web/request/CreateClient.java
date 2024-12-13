package org.dreamteam.sda.controller.web.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
@Builder
public class CreateClient {
        @NotBlank(message = "Name is required") String name;
        @NotBlank(message = "Address is required") String address;


}
