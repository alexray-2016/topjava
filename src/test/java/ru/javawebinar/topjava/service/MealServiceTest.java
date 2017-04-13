package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(lastMealId, ADMIN_ID);
        MealTestData.MATCHER.assertEquals(testMeal6, meal);
    }

    @Test
    public void testDelete() throws Exception {
        MealTestData.MATCHER.assertEquals(testMeal6, service.get(lastMealId, ADMIN_ID));
        service.delete(lastMealId, ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal();
        updated.setId(testMeal6.getId());
        updated.setCalories(testMeal6.getCalories());
        updated.setDescription(testMeal6.getDescription());
        updated.setDateTime(testMeal6.getDateTime());
        service.update(testMeal6, ADMIN_ID);
        MealTestData.MATCHER.assertEquals(updated, testMeal6);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet()
    {
        Meal meal = service.get(lastMealId, USER_ID);
        MealTestData.MATCHER.assertEquals(testMeal6, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete()
    {
        MealTestData.MATCHER.assertEquals(testMeal6, service.get(lastMealId, USER_ID));
        service.delete(lastMealId, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        Meal updated = new Meal();
        updated.setId(testMeal6.getId());
        updated.setCalories(2000);
        updated.setDescription(testMeal6.getDescription());
        updated.setDateTime(testMeal6.getDateTime());
        service.update(updated, USER_ID);
        MealTestData.MATCHER.assertEquals(updated, service.get(testMeal6.getId(), ADMIN_ID));
    }
}