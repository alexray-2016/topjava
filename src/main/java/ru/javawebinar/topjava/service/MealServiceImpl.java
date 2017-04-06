package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(mealRepository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(mealRepository.get(id, userId), id);
    }

    @Override
    public Collection<MealWithExceed> getAll(int userId) {
        return MealsUtil.getWithExceeded(mealRepository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId)
    {
        return MealsUtil.getWithExceeded(mealRepository.getBetween(startDate, endDate, startTime, endTime, userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}