package ru.javawebinar.topjava.web.meal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

import static org.junit.Assert.*;

@Transactional
public class MealRestControllerTest extends AbstractControllerTest{

    static final ModelMatcher<MealWithExceed> EXCEED_MATCHER = ModelMatcher.of(MealWithExceed.class);

    static final String REST_MEAL = MealRestController.REST_MEAL + "/";

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_MEAL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        EXCEED_MATCHER.assertCollectionEquals(MealsUtil.getWithExceeded(MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY),
                MealsUtil.getWithExceeded(mealService.getAll(UserTestData.USER_ID), MealsUtil.DEFAULT_CALORIES_PER_DAY));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_MEAL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updatedMeal = new Meal(MEAL1_ID, MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updatedMeal.setDescription("UPDATED");
        mockMvc.perform(put(REST_MEAL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMeal)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updatedMeal, mealService.get(MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.parse("2011-12-03T10:15:30");
        LocalDateTime endDateTime = LocalDateTime.parse("2015-05-30T23:15:30");
        LocalDate startDate = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime endTime = endDateTime.toLocalTime();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, AuthorizedUser.id()),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
        System.out.println(mealWithExceeds);
        mockMvc.perform(post(REST_MEAL + "filter/2011-12-03T10:15:30/2015-05-30T23:15:30"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(EXCEED_MATCHER.contentListMatcher(mealWithExceeds));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        Meal expected = new Meal(LocalDateTime.parse("2016-05-05T20:00"), "created", 500);
        ResultActions resultActions = mockMvc.perform(post(REST_MEAL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(resultActions);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MEALS.add(0, expected);
        MATCHER.assertCollectionEquals(MEALS, mealService.getAll(UserTestData.USER_ID));
        MEALS.remove(expected);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_MEAL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MEALS.remove(MEAL1);
        MATCHER.assertCollectionEquals(MEALS, mealService.getAll(UserTestData.USER_ID));
        MEALS.add(MEAL1);
    }
}