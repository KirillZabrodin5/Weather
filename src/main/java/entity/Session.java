package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Builder
@Table(name="Sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="UserId")
    private Integer userId;
    @Column(name="ExpiresAt")
    private LocalDateTime expiresAt;
}
