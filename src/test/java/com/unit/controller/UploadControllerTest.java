package com.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.controller.UploadController;
import com.domain.upload.UploadResourceNotFoundException;
import com.service.UploadService;

@ExtendWith(MockitoExtension.class)
public class UploadControllerTest {

	private MultipartFile multipartFile;
	private File file;

	@Mock
	private UploadService uploadService;

	@InjectMocks
	private UploadController uploadController;

	@BeforeEach
	void setup() throws FileNotFoundException, IOException {
		file = new File("src/test/resources/files/uploadedFiles/multipartTest.txt");
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest.txt",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
	}

	@Test
	void shouldSaveMultipartFile() throws IllegalStateException, IOException {
		when(uploadService.saveMultipartFile(multipartFile)).thenReturn(true);
		assertTrue(uploadController.handleFileUpload(multipartFile).getBody());
		verify(uploadService).saveMultipartFile(multipartFile);
	}

	@Test
	void shouldTrowResponseStatusExceptionWhenExceptionInSaveMultipartFile() throws IllegalStateException, IOException {
		when(uploadService.saveMultipartFile(multipartFile)).thenThrow(IOException.class);
		assertThrows(ResponseStatusException.class, () -> uploadController.handleFileUpload(multipartFile).getBody());
		verify(uploadService).saveMultipartFile(multipartFile);
	}

	@Test
	void shouldGetMultipartFile() throws UploadResourceNotFoundException {
		Resource fileSystemResource = new FileSystemResource(file);
		ResponseEntity<Resource> expectedFile = ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(fileSystemResource);
		when(uploadService.getMultipartFile(multipartFile.getName())).thenReturn(fileSystemResource);
		assertEquals(expectedFile, uploadController.handleFileDownload(multipartFile.getName()));
	}

	@Test
	void shouldThrowResponseStatusExceptionWhenExceptionInGetMultipartFile() throws UploadResourceNotFoundException{
		when(uploadService.getMultipartFile(multipartFile.getName())).thenThrow(UploadResourceNotFoundException.class);
		assertThrows(UploadResourceNotFoundException.class, () -> uploadController.handleFileDownload(multipartFile.getName()));
	}

	@Test
	void shouldDeleteMultipartFile() throws IllegalStateException, IOException {
		when(uploadService.deleteMultipartFile(multipartFile.getName())).thenReturn(true);
		assertTrue(uploadController.handleFileDelete(multipartFile.getName()).getBody());
		verify(uploadService).deleteMultipartFile(multipartFile.getName());
	}
}
