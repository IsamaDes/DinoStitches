package com.example.dinostitches.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostRequestDto {
    @NotEmpty
    private String content;
    private String title;



}

