package ru.javawebinar.topjava.web.meal;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.sf.ehcache.util.TimeUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_MEAL)
public class MealRestController extends AbstractMealController {
    static final String REST_MEAL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_MEAL + "/{id}")
                .buildAndExpand(meal.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriOfNewResource);

        return ResponseEntity.created(uriOfNewResource).headers(headers).body(meal);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @PostMapping(value = "/filter/{startDateTime}/{endDateTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
            @PathVariable("startDateTime") LocalDateTime startDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
            @PathVariable("endDateTime")LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime endTime = endDateTime.toLocalTime();
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}