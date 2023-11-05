package com.pokeman.api.controllers;

import com.pokeman.api.dto.ReviewDto;
import com.pokeman.api.dto.responses.ItemFormatter;
import com.pokeman.api.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ReviewController {

    protected final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("pokemon/{id}/review")
    public ItemFormatter<ReviewDto> createReview(@RequestBody ReviewDto reviewDto, @PathVariable("id") int id) {
        return this.reviewService.createReview(id, reviewDto);
    }

    @GetMapping("pokemon/{id}/review")
    public List<ReviewDto> fetchAllReviewsByPokemon(@PathVariable int id) {
        return this.reviewService.fetchAllReviewsByPokemon(id);
    }

    @GetMapping("pokemon/{pokemonId}/review/{reviewId}")
    public ItemFormatter<ReviewDto> fetchSingleReviewByPokemon(@PathVariable int pokemonId, @PathVariable int reviewId) {
        return this.reviewService.fetchSingleReviewByPokemon(reviewId, pokemonId);
    }

    @PutMapping("pokemon/{pokemonId}/review/{reviewId}/update")
    public ItemFormatter<ReviewDto> updatePokemonReview(@PathVariable int pokemonId, @PathVariable int reviewId, @RequestBody ReviewDto reviewDto) {
        return this.reviewService.updatePokemonReview(reviewId, pokemonId, reviewDto);
    }

    @DeleteMapping("pokemon/review/{reviewId}/delete")
    public ItemFormatter<String> deletePokemonReview(@PathVariable int reviewId) {
        return this.reviewService.deletePokemon(reviewId);
    }
}
