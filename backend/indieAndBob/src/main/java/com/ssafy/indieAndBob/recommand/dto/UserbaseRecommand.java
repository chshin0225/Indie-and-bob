package com.ssafy.indieAndBob.recommand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserbaseRecommand {
	private int userId;
	private int gameId;
}
