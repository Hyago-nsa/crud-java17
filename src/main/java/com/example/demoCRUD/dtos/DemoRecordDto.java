package com.example.demoCRUD.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DemoRecordDto(@NotBlank String name,@NotNull BigDecimal value) {

}
