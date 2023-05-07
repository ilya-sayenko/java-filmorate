package ru.yandex.practicum.filmorate.storage.impl.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;

@Component
public class InMemoryUserStorage extends InMemoryAbstractStorage<User> implements UserStorage {

    @Override
    public User create(User user) {
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        return super.create(user);
    }

    @Override
    public void addFriend(User user, User friend) {
        user.addFriend(friend);
        friend.addFriend(user);
    }

    @Override
    public void deleteFriend(User user, User friend) {
        user.deleteFriend(friend);
        friend.deleteFriend(user);
    }
}
