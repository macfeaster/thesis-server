package se.kth.mauritzz.thesis.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tinkId;
    private String accountId;
    private double amount;
    private String description;
    private String cleanedDescription;
    private LocalDateTime time;
    private boolean pending;
    private LocalDateTime createdAt;

}
