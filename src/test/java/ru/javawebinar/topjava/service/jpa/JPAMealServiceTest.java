package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles({Profiles.JPA, Profiles.ACTIVE_DB})
public class JPAMealServiceTest extends MealServiceTest{
}
