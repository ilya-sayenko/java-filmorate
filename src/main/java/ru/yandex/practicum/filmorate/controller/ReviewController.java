package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.impl.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value = "/{id}")
    public Review findById(@PathVariable int id) {
        log.info(FIND_REVIEW_BY_ID.getMessage());
        return reviewService.findById(id);
    }

    @GetMapping
    public List<Review> findByFilmId(@RequestParam(required = false) Integer filmId,
                                     @RequestParam(defaultValue = "10") int count) {
        log.info(FIND_REVIEW_BY_FILM_ID.getMessage());
        if (filmId != null) {
            return reviewService.findByFilmIdLimited(filmId, count);
        } else {
            return reviewService.findAllLimitedTo(count);
        }

    }

    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        log.info(REVIEW_IS_CREATED.getMessage());
        return reviewService.create(review);
    }

    @PutMapping
    public Review update(@RequestBody Review review) {
        log.info(REVIEW_IS_UPDATED.getMessage());
        return reviewService.update(review);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void createLike(@PathVariable int id, @PathVariable int userId) {
        log.info(ADD_LIKE_FOR_REVIEW.getMessage());
        reviewService.createLike(id, userId);
    }

    @PutMapping(value = "/{id}/dislike/{userId}")
    public void createDislike(@PathVariable int id, @PathVariable int userId) {
        log.info(ADD_DISLIKE_FOR_REVIEW.getMessage());
        reviewService.createDislike(id, userId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable int id) {
        log.info(DELETE_REVIEW.getMessage());
        reviewService.deleteById(id);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info(DELETE_LIKE_FOR_REVIEW.getMessage());
        reviewService.deleteLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/dislike/{userId}")
    public void deleteDislike(@PathVariable int id, @PathVariable int userId) {
        log.info(DELETE_DISLIKE_FOR_REVIEW.getMessage());
        reviewService.deleteDislike(id, userId);
    }
}
