package com.ssafy.indieAndBob.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private static String email;
    private static String name;
    private static String password;
    private static String postcode;
    private static String address;
    private static String extraAddress;
    private static String bankName;
    private static String bankAccount;
    private static String isDeveloper;
    private static String phoneNumber;
    private static String profile;
    private static String introduction;
}
