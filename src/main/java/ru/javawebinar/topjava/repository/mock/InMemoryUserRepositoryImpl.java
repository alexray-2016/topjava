package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        if (get(id) != null)
        {
            repository.remove(id);
            LOG.info("delete " + id);
            return true;
        }
        else
        {
            LOG.info("user with id " + id + " was not found");
            return false;
        }
    }

    @Override
    public User save(User user) {
        if (user.isNew())
        {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> users = repository.values().stream().collect(Collectors.toList());
        Collections.sort(users, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream()
                .filter(elem -> email.equals(elem.getEmail()))
                .findFirst().get();
    }
}
