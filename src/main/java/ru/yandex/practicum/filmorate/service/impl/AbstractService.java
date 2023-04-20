package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.service.Service;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class AbstractService<T extends Model> implements Service<T> {
    protected final Storage<T> storage;

    public List<T> findAll() {
        return storage.findAll().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public T findById(int id) throws ModelNotFoundException {
        return storage.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    @Override
    public T create(T model) {
        return storage.create(model);
    }

    @Override
    public T update(T model) throws ModelNotFoundException {
        changeNewModelBeforeUpdate(model, findById(model.getId()));
        return storage.update(model);
    }

    protected abstract void changeNewModelBeforeUpdate(T newModel, T oldModel);
}
