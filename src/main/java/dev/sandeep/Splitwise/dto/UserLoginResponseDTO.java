package dev.sandeep.Splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserLoginResponseDTO {
    private int id;
    private String name;
    private String email;
    //todo : add list of friends dto
    private List<UserFriendResponseDTO> friendList;
    //todo : add list of group dto
    private List<GroupResponseDTO> groups;
}
