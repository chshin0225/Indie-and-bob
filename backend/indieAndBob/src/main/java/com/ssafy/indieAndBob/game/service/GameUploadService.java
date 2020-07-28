package com.ssafy.indieAndBob.game.service;

import org.springframework.web.multipart.MultipartFile;

public interface GameUploadService {
	void fileupload(MultipartFile mFile);
}
