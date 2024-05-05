package xyz.suhyuk0544.springwebsocket.WebSocket.Controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.suhyuk0544.springwebsocket.Redis.Room.Room;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class WebSocketController {


    private final RoomRepository roomRepository;

//    @GetMapping(value = "/rooms")
//    public ResponseEntity<String> searchRooms() {

////        ArrayList<Room> rooms = roomRepository.findAll();
//
//        return ResponseEntity.ok(rooms.toString());
//    }

    @GetMapping(value = "/room/{id}")
    public ResponseEntity<String> searchRoom(@PathVariable String id) {

//        return ResponseEntity.ok(roomRepository.findByName(id).toString());

        return null;
    }

    @PostMapping(value = "/room/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRoom(@RequestBody Map<String,Object> json){
        JSONObject jsonObject = new JSONObject(json);

        HttpHeaders headers = new HttpHeaders();
        headers.add("roomId",jsonObject.getString("id"));
        headers.setLocation(URI.create("ws://localhost:8080/chat"));

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
