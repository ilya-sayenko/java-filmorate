package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends Model {

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private String login;

    @Nullable
    private String name;

    @PastOrPresent
    private LocalDate birthday;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<User> friends = new HashSet<>();

    @Builder(builderMethodName = "userBuilder")
    public User(int id, String email, String login, @Nullable String name, LocalDate birthday, Set<User> friends) {
        super(id);
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void deleteFriend(User friend) {
        friends.remove(friend);
    }
}
