package core;

import model.User;
import model.Room;

import java.util.List;

/**
 * @author ph0ly
 * @time 2016-09-16
 * 一个视频连接表示一个用户与视频服务器建立的一次会话
 * 在这次会话中, 可以进入多个房间
 */
public interface TVClient {

    boolean login(String userName, String password);

    boolean logout();

    User getLoginUser();

    boolean joinRoom(String roomId);

    boolean quitRoom(String roomId);

    List<Room> getJoinedRooms();

    boolean isJoined(String roomId);

}
