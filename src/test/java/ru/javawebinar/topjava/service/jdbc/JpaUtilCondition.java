package ru.javawebinar.topjava.service.jdbc;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JpaUtilCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean b = false;
        String[] profiles = context.getEnvironment().getActiveProfiles();
        for (String profile : profiles)
        {
            System.out.println(profile);
            if ("jdbc".equals(profile)) b = true;
        }
        return b;
    }
}
