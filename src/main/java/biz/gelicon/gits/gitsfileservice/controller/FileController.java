package biz.gelicon.gits.gitsfileservice.controller;

import biz.gelicon.gits.gitsfileservice.service.FileService;
import biz.gelicon.gits.gitsfileservice.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@Slf4j
public class FileController {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileService fileService;

    @Autowired
    public FileController(JwtTokenUtils jwtTokenUtils, FileService fileService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam(value = "token") String token) {
        try {
            String uncPath = jwtTokenUtils.getUncPathFromToken(token);
//            uncPath = "\\\\localhost\\C$\\Users\\novoz\\Downloads\\lain.jpg";
            File file = fileService.getFileByUncPath(uncPath);
            FileSystemResource fsr = new FileSystemResource(file);
            String mimeType = fileService.getMimeType(file);
            log.info("Try to get file with unc path: " + uncPath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header("Content-disposition", "attachment; filename=" + file.getName())
                    .body(fsr);
        } catch (ExpiredJwtException e) { //TODO ControllerAdvice
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().body("Время действия ссылки истекло");
        } catch (IOException e) {
            log.warn(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
