package com.monster.content.uploader.rest.api.controller;


import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monster.content.uploader.rest.api.model.LoginInfo;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth/v1")
@Tag(name="Login", description = "This will provide Login Info and Ticket consumer service")
public class McuLoginController {
	
	private Logger log = LoggerFactory.getLogger(McuLoginController.class);

	@Value("${otds.login-uri}")
    private String loginUrl;

    @Value("${otds.rfa}")
    private String rfa;
    
    /**
     *
     * @param redirectUri
     * @return An Url which is the 
     */
    @GetMapping(value="/loginUri", produces = { "application/json" })
    LoginInfo getRedirectUri(@RequestParam("redirectUri") String redirectUri){
        LoginInfo loginInfo = new LoginInfo();
        log.info("Inside redirect");
        String url = loginUrl.concat("?PostTicket=true&RFA=")
                             .concat(rfa)
                             .concat(":")
                             .concat(redirectUri);
        log.info("Full Url: "+url);
        loginInfo.setUrl(url);
        return loginInfo;
    }
    /**
     * @author DibyaPrakashOjha
     */
    @PostMapping("/ticketConsumer")
    void setTicketConsumer(HttpServletResponse response,
                                           @ModelAttribute("OTDSTicket") String OTDSTicket,
                                           @RequestParam("redirectUri") String redirectUri) throws IOException {
        log.info("Inside login info");
        Cookie cookie = new Cookie("OTDSTicket",OTDSTicket);
        response.addCookie(cookie);
//        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/mcu/");
        response.sendRedirect(redirectUri);
    }
    /**
     * @author DibyaPrakashOjha
     */
    @GetMapping(value="/login-test", produces = { "application/json" })
    ResponseEntity<String> getHello(@CookieValue(value = "OTDSTicket",required = false) String OTDSTicket ){
        if(OTDSTicket != null && !("".equals(OTDSTicket))){
            return ResponseEntity.ok().body("HELLO WORLD: "+OTDSTicket);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

    }
	
}
