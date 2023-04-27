package com.portfolio.postproject.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.postproject.dto.board.PostRequestDto;
import com.portfolio.postproject.service.board.WriteBoardService;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(WriteRestControllerTest.class)
class WriteRestControllerTest {

	@Mock
	private Principal principal;

	@Mock
	private WriteBoardService writeBoardService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("글 작성")
	@WithMockUser
	void boardSave() throws Exception {

		PostRequestDto postRequestDto = PostRequestDto.builder()
			.postId("1")
			.paramId("user")
			.postDate("2023년 04월 27일")
			.postTitle("TITLE")
			.postContent("CONTENT")
			.build();
		MockMultipartFile file = new MockMultipartFile("img",
			"test.png",
			"image/png",
			"test".getBytes(StandardCharsets.UTF_8));

		String postRequestDtoJson = objectMapper.writeValueAsString(postRequestDto);
		MockMultipartFile postRequestDtoJsonFile = new MockMultipartFile("data", "data", "application/json",
			postRequestDtoJson.getBytes(StandardCharsets.UTF_8));

		when(principal.getName()).thenReturn("user");
		when(writeBoardService.saveBoard(any(), any())).thenReturn(1L);

		mockMvc.perform(multipart("/board/save")
				.file(file)
				.file(postRequestDtoJsonFile)
				.principal(principal)
				.with(csrf())
				.with(oauth2Login()))
			.andExpect(status().isOk())
			.andReturn();
	}

}