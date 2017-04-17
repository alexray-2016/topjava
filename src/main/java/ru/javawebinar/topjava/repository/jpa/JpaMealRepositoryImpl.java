package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.getUser() == null) {
            User user = em.getReference(User.class, userId);
            meal.setUser(user);
        }
        if (meal.isNew())
        {
            em.persist(meal);
        }
        else
        {
            if (meal.getUser().getId() != userId)
            {
                return null;
            }
            em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal = em.getReference(Meal.class, id);
        if (meal.getUser().getId() == userId)
        {
            em.remove(meal);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal.getUser().getId() == userId) {
            return meal;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> query = em.createQuery("select m from Meal m left join fetch m.user where m.user.id=:userId order by m.dateTime desc", Meal.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        TypedQuery<Meal> query = em.createQuery("select m from Meal m left join fetch m.user where m.user.id=:userId and m.dateTime between :startDate and :endDate order by m.dateTime desc", Meal.class);
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}