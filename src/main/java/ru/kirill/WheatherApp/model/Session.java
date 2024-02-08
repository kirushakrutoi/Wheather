package ru.kirill.WheatherApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "expiresat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;
}
