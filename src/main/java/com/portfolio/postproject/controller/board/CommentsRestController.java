package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.dto.board.CommentsRequestDto;
import com.portfolio.postproject.service.board.CommentsService;
import com.portfolio.postproject.components.common.ValidationComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsRestController {

	private final CommentsService commentsService;
	private final ValidationComponent validationComponent;

	//댓글 수정
	@PutMapping("/update")
	public ResponseEntity<?> commentsUpdate(@RequestBody @Valid CommentsRequestDto commentsRequestDto,
											Errors error, Principal principal) {
		validationComponent.validation(error);
		commentsService.updateComments(commentsRequestDto, principal);

		return ResponseEntity.ok().build();
	}

	//댓글 삭제
	@DeleteMapping("/delete")
	public ResponseEntity<?> commentsDelete(@RequestParam String commentsId, Principal principal) {

		commentsService.deleteComments(commentsId, principal);
		return ResponseEntity.ok().build();
	}

	//댓글 작성
	@PostMapping("/write")
	public ResponseEntity<?> commentsWrite(@RequestBody @Valid CommentsRequestDto commentsRequestDto,
									       Errors error, Principal principal) {
		validationComponent.validation(error);
		commentsService.writeComments(commentsRequestDto, principal);

		return ResponseEntity.ok().build();
	}
}
