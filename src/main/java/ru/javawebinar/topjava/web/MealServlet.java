package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController mealRestController = applicationContext.getBean(MealRestController.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) {
            Meal meal = new Meal(AuthorizedUser.getId(), id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.save(meal);
            response.sendRedirect("meals");
        }
        else
        {
            LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"), LocalDate.MIN);
            LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"), LocalDate.MAX);
            LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"), LocalTime.MIN);
            LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"), LocalTime.MAX);
            request.setAttribute("meals", mealRestController.getBetween(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(AuthorizedUser.getId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                if (userId != null)
                {
                    AuthorizedUser.setId(Integer.parseInt(userId));
                }
                LOG.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        applicationContext.close();
        super.destroy();
    }
}
