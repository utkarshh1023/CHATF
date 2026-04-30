package com.chatt.chatt_backend.controller;

import com.chatt.chatt_backend.config.AppConstants;
import com.chatt.chatt_backend.entities.Message;
import com.chatt.chatt_backend.entities.Room;
import com.chatt.chatt_backend.payload.RoomRequest;
import com.chatt.chatt_backend.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Map<String, String> body) {
        String roomId = body.get("roomId");

        if (roomRepository.findByRoomId(roomId) != null) {
            return ResponseEntity.badRequest().body("Room exists");
        }

        Room room = new Room();
        room.setRoomId(roomId);

        return ResponseEntity.ok(roomRepository.save(room));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) return ResponseEntity.badRequest().body("Room not found");
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{roomId}/messages")
    public List<Message> getMessages(@PathVariable String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) return new ArrayList<>();
        return room.getMessages();
    }
}
