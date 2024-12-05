package org.dreamteam.sda.model;

import lombok.Builder;

@Builder
public record Client (String id, String name, String address ) {
}
