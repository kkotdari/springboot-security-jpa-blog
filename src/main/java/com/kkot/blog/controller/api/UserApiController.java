package com.kkot.blog.controller.api;

import com.kkot.blog.dto.ResponseDTO;
import com.kkot.blog.model.User;
import com.kkot.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/join-proc")
    public ResponseDTO<Integer> postUser(@RequestBody User requestUser) {
        userService.join(requestUser);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson 라이브러리)
    }

    @PutMapping("/user")
    public ResponseDTO<Integer> putUser(@RequestBody User requestUser) {
        userService.update(requestUser);
        // 트랜잭션이 종료 -> DB의 값은 변경됐으나 세션의 데이터는 변경되지 않음 -> 직접 세션의 데이터를 변경해야 함
        // 세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}
