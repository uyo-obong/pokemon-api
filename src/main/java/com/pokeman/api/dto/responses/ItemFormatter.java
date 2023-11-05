package com.pokeman.api.dto.responses;

import com.pokeman.api.dto.PokemonDto;
import lombok.Data;

@Data
public class ItemFormatter<T> {
    private int statusCode;
    private String message;
    private T data;
}
