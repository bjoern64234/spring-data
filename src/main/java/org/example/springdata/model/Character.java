package org.example.springdata.model;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record Character(String id, String name, int age, String profession) {
}
