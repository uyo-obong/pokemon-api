package com.pokeman.api.service.review;

import com.pokeman.api.dto.PokemonDto;
import com.pokeman.api.dto.ReviewDto;
import com.pokeman.api.dto.responses.CollectionFormatter;
import com.pokeman.api.dto.responses.ItemFormatter;
import com.pokeman.api.exceptions.NotFoundException;
import com.pokeman.api.models.Pokemon;
import com.pokeman.api.models.Review;
import com.pokeman.api.repository.PokemonRepository;
import com.pokeman.api.repository.ReviewRepository;
import com.pokeman.api.service.pokemon.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements ReviewInterface {

    protected final ReviewRepository reviewRepository;
    protected final PokemonRepository pokemonRepository;
    protected final PokemonService pokemonService;

    public ReviewService(ReviewRepository reviewRepository, PokemonRepository pokemonRepository, PokemonService pokemonService) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
        this.pokemonService = pokemonService;
    }

    /**
     * @param pokemonId
     * @param reviewDto
     * @return
     */
    @Override
    public ItemFormatter<ReviewDto> createReview(int pokemonId, ReviewDto reviewDto) {
        Pokemon pokemon = this.pokemonRepository.findById(pokemonId).orElseThrow(() -> new NotFoundException("Pokemon does not exist"));

        Review review = mapToEntity(reviewDto);
        review.setPokemon(pokemon);

        Review review1 = this.reviewRepository.save(review);
        return mapToFormatter(mapToDto(review1));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<ReviewDto> fetchAllReviewsByPokemon(int id) {
        List<Review> review = this.reviewRepository.findByPokemon_Id(id);
        return review.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * @param reviewId
     * @param pokemonId
     * @return
     */
    @Override
    public ItemFormatter<ReviewDto> fetchSingleReviewByPokemon(int reviewId, int pokemonId) {
        Pokemon pokemon = this.pokemonRepository.findById(pokemonId).orElseThrow(() -> new NotFoundException("Pokemon does not exist"));

        Review review = this.reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review with Id does not exist"));

        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new NotFoundException("Review with this pokemon id does not match");
        }

        return mapToFormatter(mapToDto(review));

    }

    /**
     * @param reviewId
     * @param reviewDto
     * @return
     */
    @Override
    public ItemFormatter<ReviewDto> updatePokemonReview(int reviewId, int pokemonId, ReviewDto reviewDto) {
        String message = String.format("Review with this ID %d not found", reviewId);

        Review review = this.reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException(message));
        Pokemon pokemon = this.pokemonRepository.findById(pokemonId).orElseThrow(() -> new NotFoundException(message));

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setPokemon(pokemon);

        Review review1 = this.reviewRepository.save(review);
        return mapToFormatter(mapToDto(review1));
    }

    /**
     * @param reviewId
     */
    @Override
    public ItemFormatter<String> deletePokemon(int reviewId) {
        Review review = this.reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("Review does not exist"));
        this.reviewRepository.delete(review);
        return mapToFormatter("Item has been deleted");
    }

    private ReviewDto mapToDto(Review review) {
        PokemonDto pokemonDto = this.pokemonService.mapToDto(review.getPokemon());

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        reviewDto.setPokemon(pokemonDto);

        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        return review;
    }

    // USING METHOD OVERLOAD TO HANDLE THE RESPONSE FOR THE DTO AND STRING
    private ItemFormatter<ReviewDto> mapToFormatter(ReviewDto item) {

        ItemFormatter<ReviewDto> itemFormatter = new ItemFormatter<>();
        itemFormatter.setStatusCode(HttpStatus.OK.value());
        itemFormatter.setMessage("Success");
        itemFormatter.setData(item);

        return itemFormatter;
    }

    private ItemFormatter<String> mapToFormatter(String item) {

        ItemFormatter<String> itemFormatter = new ItemFormatter<>();
        itemFormatter.setStatusCode(HttpStatus.OK.value());
        itemFormatter.setMessage("Success");
        itemFormatter.setData(item);

        return itemFormatter;
    }
}
