package dev.proj.roomservice.controller;

import dev.proj.roomservice.dto.ResponseInfo;
import dev.proj.roomservice.dto.RoomRequest;
import dev.proj.roomservice.dto.RoomResponse;
import dev.proj.roomservice.service.RoomService;
import dev.proj.roomservice.utils.RoomUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<RoomResponse> createRoom(@Valid @ModelAttribute RoomRequest roomRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(RoomResponse.builder()
                    .responseInfo(ResponseInfo.builder()
                            .message(bindingResult.getAllErrors().toString())
                            .build())
                    .room(null)
                    .build());
        }

        RoomResponse roomResponse = roomService.createRoom(roomRequest);

        if (roomResponse.getResponseInfo().getCode() == RoomUtils.ROOM_EXIST_CODE) {
            return ResponseEntity.badRequest().body(roomResponse);
        }

        return ResponseEntity.ok(roomResponse);
    }
}
