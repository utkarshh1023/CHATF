package com.chatt.chatt_backend.controller;


import com.chatt.chatt_backend.entities.Message;
import com.chatt.chatt_backend.entities.Room;
import com.chatt.chatt_backend.repo.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate template;

    @MessageMapping("/sendMessage/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, Message message) {

        Room room = roomRepository.findByRoomId(roomId);

        if (room == null) {
            Room newRoom = new Room();
            newRoom.setRoomId(roomId);
            room = roomRepository.save(newRoom);
        }

        message.setTimestamp(LocalDateTime.now());

        room.getMessages().add(message);
        roomRepository.save(room);

        template.convertAndSend("/topic/room/" + roomId, message);
    }
}