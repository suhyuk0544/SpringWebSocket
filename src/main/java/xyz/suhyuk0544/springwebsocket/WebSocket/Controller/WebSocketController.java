package xyz.suhyuk0544.springwebsocket.WebSocket.Controller;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.suhyuk0544.springwebsocket.Redis.Room.Room;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomRepository;
import xyz.suhyuk0544.springwebsocket.WebSocket.Dto.MessageDTO;

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


    @ResponseBody
    @GetMapping("/helloString")
    public String api(@RequestParam("name")String name) {
        return "hello" + name;
    }

    @ResponseBody
    @GetMapping("/hello-api")
    public String helloApi(@RequestParam("name")String name, @RequestParam("age")Integer age,@RequestParam("gender")String gender) {

        JSONObject jsonObject = new JSONObject()
                .put("name",name)
                .put("age",age)
                .put("gender",gender);

        return jsonObject.toString();
    }

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
