package dev.proj.roomservice.dto;

import dev.proj.roomservice.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private ResponseInfo responseInfo;
    private Room room;
}
