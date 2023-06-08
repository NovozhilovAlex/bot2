package biz.gelicon.gits.gitsfileservice.service.impl;

import biz.gelicon.gits.gitsfileservice.service.FileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public File getFileByUncPath(String uncPath) {
        String separator = "\\";
        String unc = uncPath.replaceAll(Pattern.quote(separator), "\\\\");
        return new File(unc);
    }

    @Override
    public String getMimeType(File file) throws IOException {
        Path path = file.toPath();
        return Files.probeContentType(path);
    }
}
