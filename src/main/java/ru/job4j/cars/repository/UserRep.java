package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

public class UserRep {
    public User findUserByEmail(String email) {
        return new HbmStore().tx(session ->
                (User) session.createQuery("from User where email = :email")
                        .setParameter("email", email)
                        .uniqueResult()
        );
    }
}
