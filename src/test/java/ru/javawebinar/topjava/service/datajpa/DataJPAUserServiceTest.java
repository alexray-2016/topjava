package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles({Profiles.DATAJPA, Profiles.ACTIVE_DB})
public class DataJPAUserServiceTest extends UserServiceTest{
}
