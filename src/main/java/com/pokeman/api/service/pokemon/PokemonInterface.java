package com.pokeman.api.service.pokemon;


import com.pokeman.api.dto.PokemonDto;
import com.pokeman.api.dto.PokemonResponse;
import com.pokeman.api.dto.responses.ItemFormatter;

public interface PokemonInterface {
    ItemFormatter createPokemon(PokemonDto pokemonDto);

    PokemonResponse fetchPokemon(int pageNo, int pageSize);

    ItemFormatter fetchSinglePokemon(int id);

    PokemonDto updatePokemon(int id, PokemonDto pokemonDto);

    void deletePokemon(int id);
}
