package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User findById(int id) throws UserNotFoundException {
        return userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        User createdUser = userStorage.create(user);
        log.info(USER_CREATED.getMessage());
        return createdUser;
    }

    public User update(User user) throws UserNotFoundException {
        Optional<User> userFromData = userStorage.findById(user.getId());
        if (userFromData.isPresent()) {
            user.setFriends(userFromData.get().getFriends());
            userStorage.update(user);
            log.info(USER_UPDATED.getMessage());
            return user;
        } else {
            throw new UserNotFoundException(user.getId());
        }
    }

    public void addFriend(int userId, int friendId) throws UserNotFoundException {
        User user = findById(userId);
        User friend = findById(friendId);
        user.addFriend(friend);
        friend.addFriend(user);
        log.info(FRIEND_ADDED.getMessage());
    }

    public void deleteFriend(int userId, int friendId) {
        User user = findById(userId);
        User friend = findById(friendId);
        user.deleteFriend(friend);
        friend.deleteFriend(user);
        log.info(FRIEND_DELETED.getMessage());
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>(findById(userId).getFriends());
        friends.sort(Comparator.comparingInt(User::getId));

        return friends;
    }

    public List<User> getCommonFriends(int id, int otherId) {
        Set<User> commonFriends = new HashSet<>(findById(id).getFriends());
        commonFriends.retainAll(findById(otherId).getFriends());

        return new ArrayList<>(commonFriends);
    }
}
