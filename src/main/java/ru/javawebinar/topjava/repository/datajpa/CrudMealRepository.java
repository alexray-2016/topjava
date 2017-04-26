package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user LEFT JOIN FETCH m.user.roles WHERE m.id=:id AND m.user.id=:userId")
    Meal findOne(@Param("id")int id,@Param("userId")int userId);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

    public List<Meal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
