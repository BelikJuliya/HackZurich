package com.hackzurich.demo2023.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextResponseDTO {
    String error;
    String answer;
}
