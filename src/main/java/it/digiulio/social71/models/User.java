package it.digiulio.social71.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private Timestamp createdOn;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Set<Whisper> whispers;
}
