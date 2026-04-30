package com.chatt.chatt_backend.repo;

import com.chatt.chatt_backend.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface RoomRepository extends MongoRepository<Room, String> {

    Room findByRoomId(String roomId);
}
