package com.pokeman.api.repository;

import com.pokeman.api.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByPokemon_Id(int pokemonId);
}
