package com.ssafy.indieAndBob.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String email;
    private String name;
    private String password;
    private String postcode;
    private String address;
    private String extraAddress;
    private String bankName;
    private String bankAccount;
    private String isDeveloper;
    private String phoneNumber;
    private String profile;
    private String introduction;
}
