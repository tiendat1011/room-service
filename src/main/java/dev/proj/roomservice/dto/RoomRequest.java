package dev.proj.roomservice.dto;

import dev.proj.roomservice.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    @NotBlank(message = "Room number cannot be empty")
    private String roomNumber;

    private String description;

    @NotNull(message = "Room type cannot be null")
    private RoomType roomType;

    @NotNull(message = "Room price cannot be null")
    private BigDecimal roomPrice;

    @NotBlank(message = "Room capacity cannot be empty")
    private String roomCapacity;

    @NotNull(message = "Image cannot be null")
    private List<MultipartFile> images;
}
