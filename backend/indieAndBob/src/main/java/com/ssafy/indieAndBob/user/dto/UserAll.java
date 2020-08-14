package com.ssafy.indieAndBob.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAll {
	private int userId;
	private String email;
    private String name;
    private String nickname;
    private String password;
    private int postcode;
    private String address;
    private String extraAddress;
    private String bankName;
    private String bankAccount;
    private boolean isDeveloper;
    private String phoneNumber;
    private String profile;
    private String introduction;
    private List<Integer> genreId;
    private List<String> genreName;
}
