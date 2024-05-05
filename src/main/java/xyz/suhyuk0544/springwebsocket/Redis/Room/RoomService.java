package xyz.suhyuk0544.springwebsocket.Redis.Room;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.suhyuk0544.springwebsocket.Principal.UserDetailsServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final UserDetailsServiceImpl userDetailsService;

    public Optional<Room> findRoomById(String id) {
        return roomRepository.findRoomById(id);
    }
    public Room addUserToRoom(String roomId) {
//        Room room = findRoomById(roomId);
//        int count = room.getCountUser();
//        if (count >= 2)
//            return roomRepository.createChatRoom();

        return null;
    }

    public String createChatRoom(Room waitRoom) {

        String roomId = UUID.randomUUID().toString();

        roomRepository.createChatRoom(roomId);
        roomRepository.enterChatRoom(roomId);

        for (int i = 0; i < 2; i++) {
            userDetailsService.selectUserInfo(waitRoom.getPrincipals());
        }
        return roomId;
    }



}
