package com.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.domain.upload.UploadRepository;
import com.domain.upload.UploadResourceNotFoundException;
import com.service.UploadService;

@ExtendWith(MockitoExtension.class)
class UploadServiceTest {


	private MultipartFile multipartFile;
	private File file;

	@Mock
	private Environment env;

	@Mock
	private UploadRepository uploadRepository;

	@InjectMocks
	private UploadService uploadService;

	@BeforeEach
	void setup() throws FileNotFoundException, IOException {
		file = new File("src/test/resources/files/uploadedFiles/multipartTest.txt");
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest.txt",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
	}

	@Test
	void shouldSaveNewMultipartFileAndDontUpdateExisting() throws IllegalStateException, IOException {
		when(env.getProperty("CATALINA_BASE")).thenReturn("src/test/resources/files");
		when(uploadRepository.saveFile(any(), any())).thenReturn(true);
		assertTrue(uploadService.saveMultipartFile(multipartFile));
		verify(uploadRepository, never()).saveFile(multipartFile, file);
	}

	@Test
	void shouldNotSaveMultipartFileWithEmptyExtension() throws IllegalStateException, IOException {
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
		when(env.getProperty("CATALINA_BASE")).thenReturn("src/test/resources/files");
		assertFalse(uploadService.saveMultipartFile(multipartFile));
		verify(uploadRepository, never()).saveFile(multipartFile, file);
	}

	@Test
	void shouldGetMultipartFile() throws UploadResourceNotFoundException {
		Resource fileSystemResource = new FileSystemResource(file);
		when(env.getProperty("CATALINA_BASE")).thenReturn("src/test/resources/files");
		when(uploadRepository.getFile(file)).thenReturn(Optional.of(fileSystemResource));
		assertEquals(fileSystemResource, uploadService.getMultipartFile(file.getName()));
	}

	@Test
	void whenGetNotExistigMultipartFileThenThrowUploadResourceNotFoundException() {
		file = new File("WrongFile");
		when(env.getProperty("CATALINA_BASE")).thenReturn("src/test/resources/files");
		when(uploadRepository.getFile(any())).thenReturn(Optional.empty());
		assertThrows(UploadResourceNotFoundException.class, () -> uploadService.getMultipartFile(file.getName()));
	}

	@Test
	void shouldUpdateMultipartFile() throws IllegalStateException, IOException {
		when(env.getProperty("CATALINA_BASE")).thenReturn("src/test/resources/files");
		when(uploadRepository.saveFile(any(), any())).thenReturn(true);
		assertTrue(uploadService.updateMultipartFile(multipartFile));
		verify(uploadRepository).saveFile(any(), any());
	}

	@Test
	void shouldDeleteMultipartFile() throws IllegalStateException, IOException {
		when(env.getProperty("CATALINA_BASE")).thenReturn("src/test/resources/files");
		when(uploadRepository.deleteFile(any())).thenReturn(true);
		assertTrue(uploadService.deleteMultipartFile("multipartTestDelete.txt"));
	}
}
