package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
public class MealTestData {

    public static int lastMealId = START_SEQ + 1;

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

    public static final Meal testMeal1 = new Meal(++lastMealId, LocalDateTime.of(2017, 4, 9, 10, 0), "Завтрак", 1000);
    public static final Meal testMeal2 = new Meal(++lastMealId, LocalDateTime.of(2017, 4, 9, 14, 0), "Обед", 660);
    public static final Meal testMeal3 = new Meal(++lastMealId, LocalDateTime.of(2017, 4, 9, 19, 0), "Ужин", 330);
    public static final Meal testMeal4 = new Meal(++lastMealId, LocalDateTime.of(2017, 4, 10, 9, 0), "Завтрак", 1000);
    public static final Meal testMeal5 = new Meal(++lastMealId, LocalDateTime.of(2017, 4, 10, 13, 0), "Обед", 660);
    public static final Meal testMeal6 = new Meal(++lastMealId, LocalDateTime.of(2017, 4, 10, 19, 20), "Ужин", 330);
}
