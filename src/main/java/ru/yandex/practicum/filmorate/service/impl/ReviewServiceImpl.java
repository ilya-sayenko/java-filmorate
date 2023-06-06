package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ReviewNotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage reviewStorage;

    @Override
    public Review findById(int id) {
        return reviewStorage.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Override
    public List<Review> findByFilmIdLimited(int filmId, int count) {
        return reviewStorage.findByFilmIdLimited(filmId, count).stream()
                .sorted(Comparator.comparingInt(Review::getUseful).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> findAllLimitedTo(int count) {
        return reviewStorage.findAllLimitedTo(count).stream()
                .sorted(Comparator.comparingInt(Review::getUseful).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Review create(Review review) {
        Review createdReview = reviewStorage.create(review);
        log.info(REVIEW_IS_UPDATED.getMessage());
        return createdReview;
    }

    @Override
    public Review update(Review review) {
        Review updatedReview = reviewStorage.update(review);
        log.info(REVIEW_IS_UPDATED.getMessage());
        return updatedReview;
    }

    @Override
    public void deleteById(int reviewId) {
        reviewStorage.deleteById(reviewId);
        log.info(REVIEW_IS_DELETED.getMessage());
    }

    @Override
    public void createLike(int reviewId, int userId) {
        reviewStorage.createLike(reviewId, userId);
        log.info(LIKE_FOR_REVIEW_IS_ADDED.getMessage());
    }

    @Override
    public void createDislike(int reviewId, int userId) {
        reviewStorage.createDislike(reviewId, userId);
        log.info(DISLIKE_FOR_REVIEW_IS_ADDED.getMessage());
    }

    @Override
    public void deleteLike(int reviewId, int userId) {
        reviewStorage.deleteLike(reviewId, userId);
        log.info(LIKE_FOR_REVIEW_IS_DELETED.getMessage());
    }

    @Override
    public void deleteDislike(int reviewId, int userId) {
        reviewStorage.deleteDislike(reviewId, userId);
        log.info(DISLIKE_FOR_REVIEW_IS_DELETED.getMessage());
    }
}
