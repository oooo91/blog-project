package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.dto.board.PostRequestDto;
import com.portfolio.postproject.dto.board.ThumbnailRequestDto;
import com.portfolio.postproject.service.board.WriteBoardService;
import com.portfolio.postproject.components.common.ValidationComponent;
import com.portfolio.postproject.service.board.ThumbnailService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
	public ResponseEntity<?> boardSave(
		@Valid @RequestPart(value = "data") PostRequestDto postRequestDto,
		@RequestPart(value = "img", required = false) MultipartFile multipartFile,
		Errors error, Principal principal) throws IOException {

		validationComponent.validation(error);
		return new ResponseEntity<>(
			String.valueOf(writeBoardService.saveBoard(postRequestDto, multipartFile)),
			HttpStatus.OK);
	}


	@PreAuthorize("isAuthenticated() and (#thumbnailRequestDto.paramId == principal.name)")
	@PutMapping("/image/update")
	public ResponseEntity<?> imageUpdate(
		@RequestPart(value = "data") ThumbnailRequestDto thumbnailRequestDto,
		@RequestPart(value = "img", required = false) MultipartFile multipartFile,
		Principal principal) throws IOException {

		thumbNailService.updateImage(thumbnailRequestDto.getPostId(), multipartFile);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("isAuthenticated() and (#thumbnailRequestDto.paramId == principal.name)")
	@DeleteMapping("/image/delete")
	public ResponseEntity<?> deleteImage(
		@RequestBody ThumbnailRequestDto thumbnailRequestDto, Principal principal) {

		thumbNailService.deleteImage(thumbnailRequestDto);
		return ResponseEntity.ok().build();
	}
}
