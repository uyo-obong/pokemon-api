package com.pokeman.api.service.pokemon;

import com.pokeman.api.dto.PokemonDto;
import com.pokeman.api.dto.PokemonResponse;
import com.pokeman.api.dto.responses.ItemFormatter;
import com.pokeman.api.exceptions.NotFoundException;
import com.pokeman.api.models.Pokemon;
import com.pokeman.api.repository.PokemonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService implements PokemonInterface {

    protected final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }


    @Override
    public ItemFormatter createPokemon(PokemonDto request) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(request.getName());
        pokemon.setType(request.getType());

        Pokemon pokemonInstance = this.pokemonRepository.save(pokemon);

        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setId(pokemonInstance.getId());
        pokemonResponse.setName(pokemonInstance.getName());
        pokemonResponse.setType(pokemonInstance.getType());

        return mapToFormatter(pokemonResponse);
    }

    /**
     * @return
     */
    @Override
    public PokemonResponse fetchPokemon(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Pokemon> pokemons = this.pokemonRepository.findAll(pageable);
        List<Pokemon> pokemonList = pokemons.getContent();
        List<PokemonDto> content = pokemonList.stream().map(this::mapToDto).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;
    }


    @Override
    public ItemFormatter fetchSinglePokemon(int id) {
        Pokemon pokemon = this.pokemonRepository.findById(id).orElseThrow(() -> new NotFoundException("Pokemon could not be found"));
        PokemonDto pokemonDto = mapToDto(pokemon);
        return mapToFormatter(pokemonDto);
    }

    /**
     * @param id
     * @param pokemonDto
     * @return
     */
    @Override
    public PokemonDto updatePokemon(int id, PokemonDto pokemonDto) {
        Pokemon pokemon = this.pokemonRepository.findById(id).orElseThrow(() -> new NotFoundException("Pokemon could not be found"));

        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon pokemon1 = this.pokemonRepository.save(pokemon);
        return mapToDto(pokemon1);
    }

    /**
     * @param id
     */
    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = this.pokemonRepository.findById(id).orElseThrow(() -> new NotFoundException("Pokemon could not be found"));
        this.pokemonRepository.delete(pokemon);
    }

    public PokemonDto mapToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return  pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDto.getId());
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }

    private ItemFormatter<PokemonDto> mapToFormatter(PokemonDto item) {

        ItemFormatter<PokemonDto> itemFormatter = new ItemFormatter<>();
        itemFormatter.setStatusCode(HttpStatus.OK.value());
        itemFormatter.setMessage("Success");
        itemFormatter.setData(item);

        return itemFormatter;
    }
}
