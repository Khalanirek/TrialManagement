package com.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.domain.upload.UploadRepository;

@ExtendWith(MockitoExtension.class)
class UploadRepositoryTest {

	private MultipartFile multipartFile;

	@Mock
	private Environment env;

	private File file;

	private UploadRepository uploadRepository = new UploadRepository();

	@BeforeEach
	void setup() throws FileNotFoundException, IOException {
		file = new File("src/test/resources/files/uploadedFiles/multipartTest.txt");
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest.txt",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
	}


	@Test
	void shouldSaveFile() throws IllegalStateException, IOException {
		MultipartFile multipartFileSpy = spy(multipartFile);
		doNothing().when(multipartFileSpy).transferTo(any(File.class));
		assertTrue(uploadRepository.saveFile(multipartFileSpy, file));
		verify(multipartFileSpy).transferTo(any(File.class));
	}
	@Test
	void shouldGetFile() {
		Resource fileSystemResource = new FileSystemResource(file);
		ResponseEntity<Resource> expectedFile = ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(fileSystemResource);
		assertEquals(expectedFile, uploadRepository.getFile(file).get());
	}

	@Test
	void shouldDeleteFile() {
		File fileSpy = spy(file);
		doReturn(true).when(fileSpy).delete();
		assertTrue(uploadRepository.deleteFile(fileSpy));
	}

}
