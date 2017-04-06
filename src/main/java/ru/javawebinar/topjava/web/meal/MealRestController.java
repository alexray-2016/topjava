package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

@Controller
public class MealRestController{

    @Autowired
    private MealService service;

    public Collection<MealWithExceed> getAll()
    {
        return service.getAll(AuthorizedUser.getId());
    }

    public Meal get(int id) throws NotFoundException
    {
        return service.get(id, AuthorizedUser.getId());
    }

    public void delete(int id) throws NotFoundException
    {
        service.delete(id, AuthorizedUser.getId());
    }

    public Meal save(Meal meal)
    {
        return service.save(meal);
    }
}