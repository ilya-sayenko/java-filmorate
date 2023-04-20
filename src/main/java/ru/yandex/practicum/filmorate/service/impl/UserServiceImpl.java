package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private static final Comparator<User> USER_COMPARATOR_BY_ID = Comparator.comparingInt(User::getId);

    @Autowired
    public UserServiceImpl(Storage<User> storage) {
        super(storage);
    }

    private void changeUserNameIfNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @Override
    protected void changeNewModelBeforeUpdate(User newUser, User oldUser) {
        newUser.setFriends(oldUser.getFriends());
        changeUserNameIfNull(newUser);
    }

    @Override
    public User findById(int id) throws UserNotFoundException {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User create(User user) {
        changeUserNameIfNull(user);
        User createdUser = super.create(user);
        log.info(USER_CREATED.getMessage());
        return createdUser;
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        try {
            User updatedUser = super.update(user);
            log.info(USER_UPDATED.getMessage());
            return updatedUser;
        } catch (ModelNotFoundException ex) {
            throw new UserNotFoundException(user.getId());
        }
    }

    @Override
    public void addFriend(int userId, int friendId) throws UserNotFoundException {
        User user = findById(userId);
        User friend = findById(friendId);
        user.addFriend(friend);
        friend.addFriend(user);
        log.info(FRIEND_ADDED.getMessage());
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = findById(userId);
        User friend = findById(friendId);
        user.deleteFriend(friend);
        friend.deleteFriend(user);
        log.info(FRIEND_DELETED.getMessage());
    }

    @Override
    public List<User> getFriends(int userId) {
        return findById(userId).getFriends()
                .stream()
                .sorted(USER_COMPARATOR_BY_ID)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        return findById(id).getFriends()
                .stream()
                .filter(user -> findById(otherId).getFriends().contains(user))
                .collect(Collectors.toUnmodifiableList());
    }
}
