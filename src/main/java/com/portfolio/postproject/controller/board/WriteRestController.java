package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.dto.board.PostRequestDto;
import com.portfolio.postproject.service.board.WriteBoardService;
import com.portfolio.postproject.components.common.ValidationComponent;
import com.portfolio.postproject.service.board.ThumbnailService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class WriteRestController {

	private final WriteBoardService writeBoardService;
	private final ValidationComponent validationComponent;
	private final ThumbnailService thumbNailService;

	@PreAuthorize("isAuthenticated() and (#postRequestDto.paramId == principal.name)")
	@PutMapping("/update")
	public ResponseEntity<?> boardUpdate(@RequestBody @Valid PostRequestDto postRequestDto,
		Errors error,
		Principal principal) {

		validationComponent.validation(error);
		writeBoardService.updateBoard(postRequestDto);

		return ResponseEntity.ok().build();
	}

	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@GetMapping("/delete/{paramId}")
	public ResponseEntity<?> boardDelete(@PathVariable("paramId") String paramId,
		Principal principal, HttpServletRequest request) {

		writeBoardService.deleteBoard(request);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("isAuthenticated() and (#postRequestDto.paramId == principal.name)")
	@PostMapping("/save")
	public ResponseEntity<?> boardSave(@RequestBody @Valid PostRequestDto postRequestDto,
		Errors error, Principal principal) {

		validationComponent.validation(error);
		return new ResponseEntity<>(String.valueOf(writeBoardService.saveBoard(postRequestDto)),
			HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@PostMapping("/image")
	public ResponseEntity<?> imageUpdate(@RequestParam("paramId") String paramId,
		@RequestParam("postId") String postId,
		@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
		Principal principal) throws IOException {
		return new ResponseEntity<>(thumbNailService.uploadImage(postId, multipartFile),
			HttpStatus.CREATED);
	}

	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@DeleteMapping("/image")
	public ResponseEntity<?> deleteImage(@RequestParam("paramId") String paramId,
		@RequestParam("postId") String postId, @RequestParam("fileName") String fileName,
		Principal principal) {
		thumbNailService.deleteImage(postId, fileName);
		return ResponseEntity.ok().build();
	}
}
