package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Administrator on 27.03.2017.
 */
public class MealServiceImpl implements MealService{

    private MealDaoImpl mealDao = new MealDaoImpl();

    @Override
    public void addMeal(Meal meal) {
        this.mealDao.addMeal(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        this.mealDao.deleteMeal(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        this.mealDao.updateMeal(meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return this.mealDao.getAllMeals();
    }

    @Override
    public Meal getMealById(int mealId) {
        return this.mealDao.getMealById(mealId);
    }
}
