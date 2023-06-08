package biz.gelicon.gits.gitsfileservice.service;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

public interface FileService {
    File getFileByUncPath(String uncPath);
    String getMimeType(File file) throws IOException;
}
