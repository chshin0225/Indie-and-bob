package com.ssafy.indieAndBob.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
	private int followId;
	private String userId;
	private String following;
}
