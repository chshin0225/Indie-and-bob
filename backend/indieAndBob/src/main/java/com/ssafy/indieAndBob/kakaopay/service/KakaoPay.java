package com.ssafy.indieAndBob.kakaopay.service;

import java.net.URI;
import java.net.URISyntaxException;

import com.ssafy.indieAndBob.game.dto.GameAll;
import com.ssafy.indieAndBob.game.service.GameService;
import com.ssafy.indieAndBob.kakaopay.dto.Funding;
import com.ssafy.indieAndBob.kakaopay.dto.KakaoPayApprovalVO;
import com.ssafy.indieAndBob.kakaopay.dto.KakaoPayReadyVO;
import com.ssafy.indieAndBob.reward.dto.Reward;
import com.ssafy.indieAndBob.reward.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
 
import lombok.extern.java.Log;
 
@Service
@Log
public class KakaoPay {
 
    private static final String HOST = "https://kapi.kakao.com";
    
    private KakaoPayReadyVO kakaoPayReadyVO;
    @Autowired
    RewardService rewardService;
    @Autowired
    GameService gameService;
    
    //결제화면
    @SuppressWarnings("deprecation")
	public String kakaoPayReady(Funding request) {
 
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "b415efabc7830344876507090a80ec5e");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");//테스트용 cid지정되어있음
        params.add("partner_order_id", Integer.toString(request.getRewardId()));//주문아이디
        params.add("partner_user_id", request.getNickname());//유저아이디
        Reward reward = rewardService.selectRewardById(request.getRewardId());
        GameAll game = gameService.selectGameById(request.getGameId());
        String rewardName = reward.getRewardName();
        String gameName = game.getName();
        params.add("item_name", gameName + " - " + rewardName);//제품명
        params.add("quantity", "1");//수량
        params.add("total_amount", Integer.toString(request.getMoney()));//가격
        params.add("tax_free_amount", "100");//세금
        //params.add("approval_url", "http://i3a105.p.ssafy.io:3000/home");//승인시갈곳
        params.add("approval_url", "http://i3a105.p.ssafy.io/api/kakaoPaySuccess?nickname="+request.getNickname()+"&gameId="+Integer.toString(request.getGameId())+"&rewardId="+Integer.toString(request.getRewardId())+"&money="+Integer.toString(request.getMoney()));
//        params.add("cancel_url", "http://i3a105.p.ssafy.io:8080");//취소시갈곳
        params.add("fail_url", "http://i3a105.p.ssafy.io/api/kakaoPayFail?nickname="+request.getNickname()+"&gameId="+Integer.toString(request.getGameId())+"&rewardId="+Integer.toString(request.getRewardId())+"&money="+Integer.toString(request.getMoney()));//실패시갈곳
        params.add("cancel_url", "http://i3a105.p.ssafy.io/api/kakaoPayCancel?nickname="+request.getNickname()+"&gameId="+Integer.toString(request.getGameId())+"&rewardId="+Integer.toString(request.getRewardId())+"&money="+Integer.toString(request.getMoney()));//실패시갈곳
 
         HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
 
        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
            
            log.info("" + kakaoPayReadyVO);

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return kakaoPayReadyVO.getNext_redirect_pc_url();
        
    }
    
    //결제정보가져오기
    @SuppressWarnings("deprecation")
	public KakaoPayApprovalVO kakaoPayInfo(String pg_token,String nickname,int orderId,int amount) {
    	 
        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");
        
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "b415efabc7830344876507090a80ec5e");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
 
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");//테스트용지정
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", Integer.toString(orderId));//주문번호
        params.add("partner_user_id", nickname);//유저아이디
        params.add("pg_token", pg_token);
        params.add("total_amount", Integer.toString(amount));//가격
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
        	KakaoPayApprovalVO kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);
          
            return kakaoPayApprovalVO;
        
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
}
 
