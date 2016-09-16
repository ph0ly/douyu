package core;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import model.Room;
import util.JsonUtils;

import java.util.Map;

/**
 * @author ph0ly
 * @time 2016-09-16
 */
public class DouyuAPIs {
    private DouyuAPIs() {}

    public static Room getRoom(String roomId) {
        Client client = Client.create();
        WebResource webResource = client.resource("http://open.douyucdn.cn/api/RoomApi/room/" + roomId);
        ClientResponse response = webResource.get(ClientResponse.class);
        String resp = response.getEntity(String.class);
        Map<String, Object> respMap = JsonUtils.toBean(resp, Map.class);
        if (respMap.get("error") == null) {
            System.out.println(resp);
            throw new RuntimeException("Response code not found");
        }
        Integer errorCode = Integer.parseInt(respMap.get("error").toString());
        if (errorCode != 0) {
            System.out.println(resp);
            throw new RuntimeException("Response code not 0");
        }
        Map<String, Object> dataMap = (Map)respMap.get("data");

        Room room = new Room();
        room.setName(Optional.fromNullable(dataMap.get("room_name")).or("").toString());
        room.setAvatar(Optional.fromNullable(dataMap.get("avatar")).or("").toString());
        room.setCateId(Optional.fromNullable(dataMap.get("cate_id")).or("").toString());
        room.setCateName(Optional.fromNullable(dataMap.get("cate_name")).or("").toString());
        room.setFansCount(Integer.parseInt(Optional.fromNullable(dataMap.get("fans_num")).or("-1").toString()));
        room.setOnline(Integer.parseInt(Optional.fromNullable(dataMap.get("online")).or("-1").toString()));
        room.setId(Optional.fromNullable(dataMap.get("room_id")).or("").toString());
        room.setOwnerName(Optional.fromNullable(dataMap.get("owner_name")).or("").toString());
        room.setStatus(Integer.parseInt(Optional.fromNullable(dataMap.get("room_status")).or("-1").toString()));

        return room;
    }

}
