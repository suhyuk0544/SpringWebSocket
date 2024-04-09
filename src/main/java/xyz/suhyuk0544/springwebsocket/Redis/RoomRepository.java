package xyz.suhyuk0544.springwebsocket.Redis;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableRedisRepositories
public interface RoomRepository extends CrudRepository<Room,String> {

    Optional<Room> findByName(String token);

//    @Override
//    ArrayList<Room> findAll();


}
