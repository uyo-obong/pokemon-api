package com.pokeman.api.controllers;

import com.pokeman.api.dto.PokemonDto;
import com.pokeman.api.dto.PokemonResponse;
import com.pokeman.api.dto.responses.ItemFormatter;
import com.pokeman.api.service.pokemon.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    protected final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }


    @GetMapping()
    public ResponseEntity<PokemonResponse> getPokemon(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize

    ) {
        return ResponseEntity.ok(this.pokemonService.fetchPokemon(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    public ItemFormatter pokenmonDetail(@PathVariable("id") int id) {
       return this.pokemonService.fetchSinglePokemon(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ItemFormatter> createPokemon(@RequestBody PokemonDto pokemon) {
        return new ResponseEntity<>(this.pokemonService.createPokemon(pokemon), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemon, @PathVariable("id") int pokemonId) {
        PokemonDto pokemonDto = this.pokemonService.updatePokemon(pokemonId, pokemon);
        return ResponseEntity.ok(pokemonDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId) {
        this.pokemonService.deletePokemon(pokemonId);
        return ResponseEntity.ok("Pokemon has been deleted");
    }
}
