package ru.job4j.cars.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false, updatable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false, updatable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "body_id", nullable = false, updatable = false)
    private Body body;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    private int mileage;

    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", created=" + created
                + ", description=" + description
                + ", user=" + user
                + ", car=" + car
                + ", model=" + model
                + ", body=" + body
                + ", photo=" + photo
                + ", mileage=" + mileage
                + ", status=" + status
                + '}';
    }
}
