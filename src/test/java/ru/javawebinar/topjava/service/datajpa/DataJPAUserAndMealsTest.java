package ru.javawebinar.topjava.service.datajpa;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.ServiceTest;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles({Profiles.DATAJPA, Profiles.ACTIVE_DB})
public class DataJPAUserAndMealsTest extends ServiceTest{

    @Before
    public void setUp() throws Exception {
        userService.evictCache();
        mealService.evictCache();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };

    @Autowired
    private MealService mealService;

    @Autowired
    private UserService userService;

    @Test
    public void getUserWithMealsById(){
        User user = userService.get(USER_ID);
        System.out.println("======== TEST getUserWithMealsById() ============");
        System.out.println(user);
        System.out.println(user.getMeals());
        System.out.println("=================================================");
        UserTestData.MATCHER.assertEquals(USER, user);

    }

    @Test
    public void getMealWithUserById(){
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        System.out.println("======== TEST getMealWithUserById() ============");
        System.out.println(meal);
        System.out.println(meal.getUser());
        System.out.println("=================================================");
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));
    }

}