package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    //Id generator
    private static AtomicInteger atomicId = new AtomicInteger(0);

    private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "Завтрак", 1200),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 13, 0), "Обед", 350),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 10, 0), "Завтрак", 800),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 13, 0), "Обед", 650),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 3, 13, 0), "Обед", 650),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 3, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 3, 9, 0), "Завтрак", 1050),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 5, 13, 0), "Обед", 1650),
            new Meal(LocalDateTime.of(2015, Month.JUNE, 5, 20, 0), "Ужин", 300)
    );

    public static AtomicInteger getAtomicId() {
        return atomicId;
    }

    private static CopyOnWriteArrayList<Meal> concurrentMeals = new CopyOnWriteArrayList<>(meals);

    public static CopyOnWriteArrayList<Meal> getMeals() {
        return concurrentMeals;
    }

    public static CopyOnWriteArrayList<MealWithExceed> getFilteredWithExceeded(CopyOnWriteArrayList<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        List<MealWithExceed> mealWithExceedList = meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return new CopyOnWriteArrayList<>(mealWithExceedList);
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    //returns MealWithExceed sorted by date list
    public static CopyOnWriteArrayList<MealWithExceed> getFullMealsWithExceedSortedList(CopyOnWriteArrayList<Meal> meals)
    {
        CopyOnWriteArrayList<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        Collections.sort(mealWithExceeds);
        return mealWithExceeds;
    }
}