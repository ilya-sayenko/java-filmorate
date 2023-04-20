package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.yandex.practicum.filmorate.validator.FilmReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Film extends Model {
    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @FilmReleaseDate
    private LocalDate releaseDate;

    @Positive
    private int duration;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<User> likes = new HashSet<>();

    @Builder(builderMethodName = "filmBuilder")
    public Film(int id, String name, String description, LocalDate releaseDate, int duration, Set<User> likes) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
    }

    public void addLike(User user) {
        likes.add(user);
    }

    public void deleteLike(User user) {
        likes.remove(user);
    }
}
