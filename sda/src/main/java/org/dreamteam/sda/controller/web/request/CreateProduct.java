package org.dreamteam.sda.controller.web.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class CreateProduct {
        @NotBlank(message = "Name is required") String name;
        @NotBlank(message = "Price is required") String price;


}
