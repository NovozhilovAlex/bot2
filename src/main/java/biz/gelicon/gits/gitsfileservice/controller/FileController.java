package biz.gelicon.gits.gitsfileservice.controller;

import biz.gelicon.gits.gitsfileservice.service.FileService;
import biz.gelicon.gits.gitsfileservice.utils.JwtTokenUtils;
import biz.gelicon.gits.gitsfileservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class FileController {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileService fileService;
    private final Utils utils;

    @Autowired
    public FileController(JwtTokenUtils jwtTokenUtils, FileService fileService, Utils utils) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.fileService = fileService;
        this.utils = utils;
    }

    @GetMapping(value = "/extract/{token}")
    public ResponseEntity<?> getFile(@PathVariable(value = "token") String token) throws IOException {
        String uncPath = jwtTokenUtils.getUncPathFromToken(token);
        File file = fileService.getFileByUncPath(uncPath);
        FileSystemResource fsr = new FileSystemResource(file);
        String mimeType = fileService.getMimeType(file);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                utils.fileNameToLat(file.getName()));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(fsr);
    }
}
