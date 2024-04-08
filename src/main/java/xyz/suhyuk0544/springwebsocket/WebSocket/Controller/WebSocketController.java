package xyz.suhyuk0544.springwebsocket.WebSocket.Controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.suhyuk0544.springwebsocket.WebSocket.Redis.Room;
import xyz.suhyuk0544.springwebsocket.WebSocket.Redis.RoomRepository;
import xyz.suhyuk0544.springwebsocket.WebSocket.Room.WebSocketRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class WebSocketController {

    private final RoomRepository roomRepository;

//    public static final HashMap<String, WebSocketRoom> rooms = new HashMap<>();

//    @GetMapping(value = "/rooms")
//    public ResponseEntity<String> searchRooms() {

//        refreshTokenRepository.findAllByAuthId();
//        ArrayList<Room> rooms = roomRepository.findAll();

//        return ResponseEntity.ok(rooms.toString());
//    }

    @GetMapping(value = "/room/{id}")
    public ResponseEntity<String> searchRoom(@PathVariable String id) {

        return ResponseEntity.ok(roomRepository.findByName(id).toString());

    }

    @PostMapping(value = "/room/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRoom(@RequestBody Map<String,Object> json){
        JSONObject jsonObject = new JSONObject(json);

        roomRepository.save(Room.builder()
                        .ttl(86400L)
                        .name(jsonObject.getString("name"))
                .build());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
