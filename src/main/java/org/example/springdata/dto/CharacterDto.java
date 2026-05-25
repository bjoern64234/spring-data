package org.example.springdata.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record CharacterDto(String name, int age, String profession) {
}
