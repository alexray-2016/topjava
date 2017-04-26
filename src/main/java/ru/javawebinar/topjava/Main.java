package ru.javawebinar.topjava;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
    }
}
