package com.portfolio.postproject.controller.admin;

import com.portfolio.postproject.dto.admin.AdminUpdateStatusRequestDto;
import com.portfolio.postproject.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

	private final AdminService adminService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/update-userStatus")
	public ResponseEntity<?> updateUserStatus(
		@RequestBody AdminUpdateStatusRequestDto adminUpdateStatusRequestDto) {
		adminService.updateUserStatus(adminUpdateStatusRequestDto);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete-board")
	public ResponseEntity<?> deleteBoard(@RequestParam String postId) {
		adminService.deleteBoard(postId);
		return ResponseEntity.ok().build();
	}
}
