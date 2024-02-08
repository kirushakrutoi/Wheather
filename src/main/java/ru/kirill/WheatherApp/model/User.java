package ru.kirill.WheatherApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Location> locations;

    @OneToOne(mappedBy = "user")
    private Session session;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Session session) {
        this.email = email;
        this.password = password;
        this.session = session;
    }
}
