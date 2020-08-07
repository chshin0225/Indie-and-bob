package com.ssafy.indieAndBob.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGenre {
	private String nickname;
	private int genreId;
}
