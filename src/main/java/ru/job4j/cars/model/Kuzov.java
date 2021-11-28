package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "kuzov")
public class Kuzov {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static Kuzov of(String name) {
        Kuzov kuzov = new Kuzov();
        kuzov.setName(name);
        return kuzov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kuzov kuzov = (Kuzov) o;
        return id == kuzov.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
