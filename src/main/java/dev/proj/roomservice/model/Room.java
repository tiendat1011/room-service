package dev.proj.roomservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "room_number")
    private String roomNumber;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "room_price")
    private BigDecimal roomPrice;

    @Column(name = "room_capacity")
    private String roomCapacity;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
}
