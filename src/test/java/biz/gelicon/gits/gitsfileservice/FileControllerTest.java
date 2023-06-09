package biz.gelicon.gits.gitsfileservice;

import biz.gelicon.gits.gitsfileservice.controller.FileController;
import biz.gelicon.gits.gitsfileservice.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FileController controller;
    @Autowired
    private JwtTokenUtils utils;

    @Test
    public void controllerIsNotNullTest() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getFileWithValidToken() throws Exception {
        String token = utils.generateToken("src/test/resources/files/test_file1.xlsx", Duration.ofMinutes(1));
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getFileWithExpiredToken() throws Exception {
        String token = utils.generateToken("src/test/resources/files/test_file1.xlsx", Duration.ofMillis(10));
        Thread.sleep(15);
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ExpiredJwtException));
    }

    @Test
    public void getFileWithInvalidToken() throws Exception {
        String token = utils.generateToken("src/test/resources/files/test_file1.xlsx", Duration.ofMinutes(1));
        token += "a";
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SignatureException));
    }

    @Test
    public void getFileWithIncorrectToken() throws Exception {
        String token = "kdjk4t4k.dkfkla5krkg";
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MalformedJwtException));
    }

    @Test
    public void getNonexistentFile() throws Exception {
        String token = utils.generateToken("src/test/resources/files/nonexistent.txt", Duration.ofMinutes(1));
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof FileNotFoundException));
    }

    @Test
    public void getFileWithNonexistentMimeType() throws Exception {
        String token = utils.generateToken("src/test/resources/files/test_file2.eml", Duration.ofMinutes(1));
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getFileWithCyrillicAlphabetName() throws Exception {
        String token = utils.generateToken("src/test/resources/files/тестовый_файл.txt", Duration.ofMinutes(1));
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getFileWithoutFormat() throws Exception {
        String token = utils.generateToken("src/test/resources/files/test_file3", Duration.ofMinutes(1));
        this.mockMvc.perform(get("/extract/" + token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
