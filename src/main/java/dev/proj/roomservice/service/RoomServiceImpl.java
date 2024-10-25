package dev.proj.roomservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.proj.roomservice.dto.ResponseInfo;
import dev.proj.roomservice.dto.RoomRequest;
import dev.proj.roomservice.dto.RoomResponse;
import dev.proj.roomservice.model.Room;
import dev.proj.roomservice.model.RoomStatus;
import dev.proj.roomservice.repository.RoomRepository;
import dev.proj.roomservice.utils.RoomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final Cloudinary cloudinary;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Optional<Room> roomNumberExist = roomRepository.findByRoomNumber(roomRequest.getRoomNumber());

        if (roomNumberExist.isPresent()) {
            return RoomResponse.builder()
                    .responseInfo(ResponseInfo.builder()
                            .message(RoomUtils.ROOM_EXIST_MESSAGE)
                            .code(RoomUtils.ROOM_EXIST_CODE)
                            .build())
                    .room(null)
                    .build();
        }

        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (MultipartFile image: roomRequest.getImages()) {
            CompletableFuture<String> imageUrlFuture = uploadImage(image);
            futures.add(imageUrlFuture);
        }

        List<String> imageUrls = futures.stream().map(CompletableFuture::join).toList();
        Room newRoom = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .description(roomRequest.getDescription())
                .roomType(roomRequest.getRoomType())
                .roomPrice(roomRequest.getRoomPrice())
                .roomCapacity(roomRequest.getRoomCapacity())
                .roomStatus(RoomStatus.AVAILABLE)
                .imageUrls(imageUrls)
                .build();

        Room savedRoom = roomRepository.save(newRoom);

        return RoomResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(RoomUtils.CREATED_ROOM_MESSAGE)
                        .code(RoomUtils.CREATED_ROOM_CODE)
                        .build())
                .room(savedRoom)
                .build();
    }

    @Async
    public CompletableFuture<String> uploadImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            return CompletableFuture.completedFuture(imageUrl);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public RoomResponse updateRoom(Long Id, RoomRequest roomRequest) {
        Optional<Room> room = roomRepository.findById(Id);

        if (room.isEmpty()) {
            return RoomResponse.builder()
                    .responseInfo(ResponseInfo.builder()
                            .message(RoomUtils.ROOM_NOT_EXIST_MESSAGE)
                            .code(RoomUtils.ROOM_NOT_EXIST_CODE)
                            .build())
                    .room(null)
                    .build();
        }

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (MultipartFile image: roomRequest.getImages()) {
            CompletableFuture<String> imageUrlFuture = uploadImage(image);
            futures.add(imageUrlFuture);
        }

        List<String> imageUrls = futures.stream().map(CompletableFuture::join).toList();

        Room newRoom = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .description(roomRequest.getDescription())
                .roomType(roomRequest.getRoomType())
                .roomPrice(roomRequest.getRoomPrice())
                .roomCapacity(roomRequest.getRoomCapacity())
                .imageUrls(imageUrls)
                .build();

        Room updatedRoom = roomRepository.save(newRoom);

        return RoomResponse.builder()
                .responseInfo(ResponseInfo.builder()
                        .message(RoomUtils.UPDATED_ROOM_MESSAGE)
                        .code(RoomUtils.UPDATED_ROOM_CODE)
                        .build())
                .room(updatedRoom)
                .build();
    }

    @Override
    public RoomResponse deleteRoom(Long Id) {
        return null;
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return List.of();
    }
}
