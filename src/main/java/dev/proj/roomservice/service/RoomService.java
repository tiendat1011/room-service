package dev.proj.roomservice.service;

import dev.proj.roomservice.dto.RoomRequest;
import dev.proj.roomservice.dto.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(RoomRequest roomRequest);
    RoomResponse updateRoom(Long Id, RoomRequest roomRequest);
    RoomResponse deleteRoom(Long id);
    List<RoomResponse> getAllRooms();
}
