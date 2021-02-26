package it.digiulio.social71.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "whispers")
public class Whisper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;
}
