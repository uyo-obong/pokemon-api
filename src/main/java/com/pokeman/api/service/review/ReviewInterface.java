package com.pokeman.api.service.review;

import com.pokeman.api.dto.ReviewDto;
import com.pokeman.api.dto.responses.CollectionFormatter;
import com.pokeman.api.dto.responses.ItemFormatter;

import java.util.List;

public interface ReviewInterface {
    ItemFormatter<ReviewDto> createReview(int pokemonId, ReviewDto reviewDto);
    List<ReviewDto> fetchAllReviewsByPokemon(int id);
    ItemFormatter<ReviewDto> fetchSingleReviewByPokemon(int reviewId, int pokemonId);

    ItemFormatter<ReviewDto> updatePokemonReview(int reviewId, int pokemonId, ReviewDto reviewDto);

    ItemFormatter<String> deletePokemon(int reviewId);
}
