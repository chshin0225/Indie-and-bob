package com.ssafy.indieAndBob.community.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityComment {
	private int ccId;
    private int communityId;
    private String nickname;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
