package com.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import base.MvcIntegrationTest;

@ExtendWith(MockitoExtension.class)
public class UploadIntegrationTest extends MvcIntegrationTest {

	private static final String UPLOAD_URI = "/files";
	@Mock
	private MockMultipartFile multipartFile;
	private File file;
	FileSystemResource fileSystemResource;

	@BeforeEach
	@Override
	public void setUp() {
		super.setUp();
		file = new File("src/test/resources/files/uploadedFiles/multipartTest.txt");
		fileSystemResource = new FileSystemResource(file);
	}

	@Test
	void shouldSaveMultipartFile() throws Exception {
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest.txt",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
		MvcResult postResult = multipartPost(UPLOAD_URI + "/upload").performMultipartRequest(multipartFile);
		assertEquals(HttpStatus.OK.value(), postResult.getResponse().getStatus());
		assertEquals("true", postResult.getResponse().getContentAsString());
	}

	@Test
	void shouldNotSaveMultipartFileIfWrongName() throws Exception {
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
		MvcResult postResult = multipartPost(UPLOAD_URI + "/upload").performMultipartRequest(multipartFile);
		assertEquals(HttpStatus.OK.value(), postResult.getResponse().getStatus());
		assertEquals("false", postResult.getResponse().getContentAsString());
	}

	@Test
	void shouldThrowExceptionWhenSaveWithForbidenName() throws Exception {
		multipartFile = new MockMultipartFile("multipartFile",
				"  . ",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
		MvcResult postResult = multipartPost(UPLOAD_URI + "/upload").performMultipartRequest(multipartFile);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), postResult.getResponse().getStatus());
	}

	@Test
	void shouldGetMultipartFile() throws Exception {
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTest.txt",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTest.txt")));
		multipartPost(UPLOAD_URI + "/upload").performMultipartRequest(multipartFile);
		MvcResult getResult = get(UPLOAD_URI + "/download/multipartTest.txt").performMultipart();
		assertEquals(HttpStatus.OK.value(), getResult.getResponse().getStatus());
	}

	@Test
	void shouldNotGetMultipartFileIfNotExists() throws Exception {
		// Removed txt because of MvcResult. It gests extension to calculate acceptable type. If txt it accept plain/text
		MvcResult getResult = get(UPLOAD_URI + "/download/notExists").performMultipart();
		assertEquals(HttpStatus.NOT_FOUND.value(), getResult.getResponse().getStatus());
	}

	@Test
	void shouldDeleteMultpartFile() throws Exception {
		multipartFile = new MockMultipartFile("multipartFile",
				"multipartTestDelete.txt",
				"multipart/form-data",
				new FileInputStream(ResourceUtils.getFile("classpath:files/multipartTestDelete.txt")));
		multipartPost(UPLOAD_URI + "/upload").performMultipartRequest(multipartFile);
		MvcResult deleteResult = delete(UPLOAD_URI + "/delete/multipartTestDelete.txt").performMultipart();
		assertEquals(HttpStatus.OK.value(), deleteResult.getResponse().getStatus());
		assertEquals("true", deleteResult.getResponse().getContentAsString());
	}

	@Test
	void shouldNotDeleteMultipartFileIfNotExists() throws Exception {
		MvcResult deleteResult = delete(UPLOAD_URI + "/delete/notExists.txt").performMultipart();
		assertEquals(HttpStatus.OK.value(), deleteResult.getResponse().getStatus());
		assertEquals("false", deleteResult.getResponse().getContentAsString());
	}
}
