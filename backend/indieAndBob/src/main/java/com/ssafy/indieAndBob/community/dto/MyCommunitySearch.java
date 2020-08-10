package com.ssafy.indieAndBob.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCommunitySearch {
	private String nickname;
	private int page;
}
