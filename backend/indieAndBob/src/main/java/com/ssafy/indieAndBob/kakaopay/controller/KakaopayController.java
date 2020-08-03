package com.ssafy.indieAndBob.kakaopay.controller;


import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.kakaopay.dto.KakaoPayApprovalVO;
import com.ssafy.indieAndBob.kakaopay.service.KakaoPay;
import com.ssafy.indieAndBob.response.dto.BasicResponse;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import lombok.extern.java.Log;

@CrossOrigin(origins = { "http://i3a105.p.ssafy.io:3000" })
@Log
@RestController
public class KakaopayController {
	@Setter(onMethod_ = @Autowired)
	
    private KakaoPay kakaopay;
    
    @PostMapping("/kakaoPay")
    @ApiOperation(value = "결제하기")
    public Object kakaoPay(@RequestBody Funding request) {
        log.info("kakaoPay post............................................");
        log.info("funding : " + request);
        ResponseEntity response = null;
        String toPay = kakaopay.kakaoPayReady(request);
		if( toPay != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = toPay;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
 
    }
    
    @GetMapping("/kakaoPaySuccess")//결제가된다면 url에 pg_token이 포함되어있을것이다
    @ApiOperation(value = "결제내역")
    public Object kakaoPaySuccess(@RequestParam("pg_token") String pg_token,@RequestParam("nickname") String nickname,
    		@RequestParam("orderId") int orderId,
    		@RequestParam("amount") int amount,Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        KakaoPayApprovalVO info = kakaopay.kakaoPayInfo(pg_token,nickname,orderId,amount);
        model.addAttribute("info", info);
       // model.addAttribute("info",kakaopay.kakaoPayInfo(pg_token));//정보들
        ResponseEntity response = null;
      
        if( info != null) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
			result.data = "success";
			result.object = info;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
        
    }
}
