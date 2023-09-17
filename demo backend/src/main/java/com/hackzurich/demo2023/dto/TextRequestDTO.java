package com.hackzurich.demo2023.dto;

import lombok.Data;

import java.util.Base64;

@Data
public class TextRequestDTO {
    String message;
    String image;
}
