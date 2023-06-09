package biz.gelicon.gits.gitsfileservice.service.impl;

import biz.gelicon.gits.gitsfileservice.service.FileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public File getFileByUncPath(String uncPath) throws FileNotFoundException {
        String separator = "\\";
        String unc = uncPath.replaceAll(Pattern.quote(separator), "\\\\");
        File file = new File(unc);
        if (!file.exists()) {
            throw new FileNotFoundException("File with unc path: " + unc + " not found");
        }
        return file;
    }

    @Override
    public String getMimeType(File file) throws IOException {
        Path path = file.toPath();
        String mimeType = Files.probeContentType(path);
        if (mimeType == null) {
            return "application/octet-stream";
        }
        return mimeType;
    }
}
