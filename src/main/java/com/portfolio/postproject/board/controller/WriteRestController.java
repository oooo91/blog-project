package com.portfolio.postproject.board.controller;

import com.portfolio.postproject.board.dto.PostReqeustDto;
import com.portfolio.postproject.board.service.WriteBoardService;
import com.portfolio.postproject.common.component.ValidationComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class WriteRestController {

	private final WriteBoardService writeBoardService;
    private final ValidationComponent validationComponent;

	//수정
	@PreAuthorize("isAuthenticated() and (#param.paramId == principal.username)")
	@PutMapping("/update")
	public ResponseEntity<?> boardUpdate(@RequestBody @Valid PostReqeustDto postReqeustDto, Errors error,
		Principal principal) {

        validationComponent.validation(error);
		writeBoardService.updateBoard(postReqeustDto);

		return ResponseEntity.ok().build();
	}

	//삭제
	@PreAuthorize("isAuthenticated() and (#paramId == principal.username)")
	@GetMapping("/delete/{paramId}")
	public ResponseEntity<?> boardDelete(@PathVariable("paramId") String paramId,
		Principal principal, HttpServletRequest request) {

		writeBoardService.deleteBoard(request);
		return ResponseEntity.ok().build();
	}

	//저장
	@PreAuthorize("isAuthenticated() and (#param.paramId == principal.username)")
	@PostMapping("/save")
	public ResponseEntity<?> boardSave(@RequestBody @Valid PostReqeustDto postReqeustDto, Errors error,
		Principal principal) {

		validationComponent.validation(error);
		return new ResponseEntity<>(String.valueOf(writeBoardService.saveBoard(postReqeustDto)), HttpStatus.OK);
	}
}
