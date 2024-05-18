package dev.sandeep.Splitwise.mapper;

import dev.sandeep.Splitwise.dto.GroupResponseDTO;
import dev.sandeep.Splitwise.dto.UserFriendResponseDTO;
import dev.sandeep.Splitwise.dto.UserLoginResponseDTO;
import dev.sandeep.Splitwise.entity.Group;
import dev.sandeep.Splitwise.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EntityDTOMapper {
    //convert to DTO
    public static UserLoginResponseDTO toDTO(User user){
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setName(user.getName());
        //conversion of user friends to friend list
        List<UserFriendResponseDTO> friendList = new ArrayList<>();
        if(user.getFriends() !=null){
            for (User friend: user.getFriends()){
                friendList.add(toFriendDTO(friend));
            }
            responseDTO.setFriendList(friendList);
        }
        //conversion of group to groupResponseDTo
        if(user.getGroups() != null){
            List<GroupResponseDTO> groups = new ArrayList<>();
            for(Group group:user.getGroups()){
                groups.add(toGroupDTO(group));
            }
            responseDTO.setGroups(groups);
        }
        return  responseDTO;
    }

    public static UserFriendResponseDTO toFriendDTO(User user){
        UserFriendResponseDTO responseDTO = new UserFriendResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        return responseDTO;
    }

    public static GroupResponseDTO toGroupDTO(Group group){
        return null;
    }
}
