package com.ssafy.indieAndBob.game.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GameUploadServiceImpl implements GameUploadService {

	@Override
	public void fileupload(MultipartFile mFile) {
		try {
			mFile.transferTo(new File("/static/img/"+mFile.getOriginalFilename()));
		}catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

}
