package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 25.03.2017.
 */
public class MealDaoImpl implements MealDao {

    static final Logger LOG = LoggerFactory.getLogger(MealDaoImpl.class);

    @Override
    public void addMeal(Meal meal) {
        meal.setId(MealsUtil.getAtomicId().incrementAndGet());
        getAllMeals().add(meal);
        LOG.info("Meal successfully saved. Meal details: " + meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        Meal meal = getMealById(mealId);
        if (meal != null) getAllMeals().remove(meal);
        LOG.info("Meal successfully deleted. Meal details: " + meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        int index = getAllMeals().indexOf(getMealById(meal.getId()));
        deleteMeal(meal.getId());
        getAllMeals().add(index, meal);
        LOG.info("Meal successfully updated. Meal details: " + meal);
    }

    @Override
    public CopyOnWriteArrayList<Meal> getAllMeals() {
        return MealsUtil.getMeals();
    }

    @Override
    public Meal getMealById(int mealId) {
        Meal meal = null;
        for (Meal mealFound : getAllMeals())
        {
            if (mealFound.getId() == mealId)
            {
                meal = mealFound;
            }
        }
        LOG.info("Meal successfully loaded. Meal details: " + meal);
        return meal;
    }
}
