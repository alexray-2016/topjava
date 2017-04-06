package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) != null)
        {
            repository.remove(id);
            return true;
        }
        else
            return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId() == userId)
        {
            return meal;
        }
        else
            return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> meals = repository.values().stream()
                .filter(elem -> elem.getUserId() == userId)
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
        return meals.isEmpty() ? Collections.emptyList() : meals;
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        return getAll(userId).stream()
                .filter(elem -> DateTimeUtil.isBetween(elem.getDate(), startDate, endDate) && DateTimeUtil.isBetween(elem.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}

