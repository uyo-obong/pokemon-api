package com.pokeman.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewDto {
    private int id;
    private String title;
    private String content;
    private  String stars;
    private PokemonDto pokemon;
}
