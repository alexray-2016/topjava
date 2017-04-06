package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    Collection<MealWithExceed> getAll(int userId);

    public Collection<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId);
}