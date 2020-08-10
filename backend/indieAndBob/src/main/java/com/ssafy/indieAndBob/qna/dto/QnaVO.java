package com.ssafy.indieAndBob.qna.dto;





import java.util.Date;

import com.ssafy.indieAndBob.user.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaVO{
	private int qnaId;
	private int gameId;
	private int numOfContents;
	private String nickname;
	private String title;
	private String content;
	private boolean isSecret;
	private String answer;
	private Date createAt;
	private Date answerAt;
}
