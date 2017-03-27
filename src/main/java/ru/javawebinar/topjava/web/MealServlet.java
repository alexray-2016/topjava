package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static List<MealWithExceed> mealWithExceeds;

    private static String INSERT_OR_EDIT = "/add_or_edit_meals.jsp";

    private static String LIST_MEAL = "/meals.jsp";

    private static int nextId;

    private MealServiceImpl mealService = new MealServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //Getting meal instance from add_or_edit_meals.jsp
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.from(TimeUtil.dateTimeFormatter.parse(request.getParameter("dateTime"))));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealId");

        //Making decision about adding or editing meal
        //nextId is always has (last ID + 1) value

        //if meal ID parsed from add_or_edit_meals.jsp equals to next ID
        if (Integer.parseInt(mealId) == nextId)
        {
            mealService.addMeal(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(mealId));
            mealService.updateMeal(meal);
        }
        //Refreshing meal list and forwarding to meal list
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        mealWithExceeds = MealsUtil.getFullMealsWithExceedSortedList(mealService.getAllMeals());
        request.setAttribute("meals", mealWithExceeds);
        view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String forward = "";
        String action = request.getParameter("action");
        nextId = MealsUtil.getAtomicId().get() + 1;

        if (action.equalsIgnoreCase("delete"))
        {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealService.deleteMeal(mealId);
            forward = LIST_MEAL;
            mealWithExceeds = MealsUtil.getFullMealsWithExceedSortedList(mealService.getAllMeals());
            request.setAttribute("meals", mealWithExceeds);
            request.getRequestDispatcher(forward).forward(request, response);
        }
        else if (action.equalsIgnoreCase("edit"))
        {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealService.getMealById(mealId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher(forward).forward(request, response);
        }
        else if (action.equalsIgnoreCase("meals"))
        {
            forward = LIST_MEAL;
            mealWithExceeds = MealsUtil.getFullMealsWithExceedSortedList(mealService.getAllMeals());
            request.setAttribute("meals", mealWithExceeds);
            request.getRequestDispatcher(forward).forward(request, response);
        }
        else
        {
            //Add operation
            //Setting attribute of new meal ID field
            request.setAttribute("nextId", nextId);
            forward = INSERT_OR_EDIT;
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}
