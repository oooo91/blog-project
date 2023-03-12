package com.portfolio.postproject.user.controller;

import com.portfolio.postproject.user.dto.JoinRequestDto;
import com.portfolio.postproject.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignUpRestController {

    private final JoinService joinService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> userJoinTry(@RequestBody @Valid JoinRequestDto joinRequestDto) {

        joinService.saveUserInfo(joinRequestDto);
        return ResponseEntity.ok().build();
    }

}
