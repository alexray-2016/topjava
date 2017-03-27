package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 27.03.2017.
 */
public interface MealService {
    public void addMeal(Meal meal);
    public void deleteMeal(int mealId);
    public void updateMeal(Meal meal);
    public CopyOnWriteArrayList<Meal> getAllMeals();
    public Meal getMealById(int mealId);
}
