package com.portfolio.postproject.controller.admin;

import com.portfolio.postproject.dto.admin.AdminDeleteBoardRequestDto;
import com.portfolio.postproject.dto.admin.AdminUpdateStatusRequestDto;
import com.portfolio.postproject.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

	private final AdminService adminService;

	//상태 수정
	//권한 설정
	@PostMapping("/update-userStatus")
	public ResponseEntity<?> updateUserStatus(
		@RequestBody AdminUpdateStatusRequestDto adminUpdateStatusRequestDto) {
		adminService.updateUserStatus(adminUpdateStatusRequestDto);
		return ResponseEntity.ok().build();
	}


	//글 삭제
	//권한 설정
	@PostMapping("/delete-board")
	public ResponseEntity<?> deleteBoard(
		@RequestBody AdminDeleteBoardRequestDto deleteBoardRequestDto) {
		adminService.deleteBoard(deleteBoardRequestDto);
		return ResponseEntity.ok().build();
	}
}
