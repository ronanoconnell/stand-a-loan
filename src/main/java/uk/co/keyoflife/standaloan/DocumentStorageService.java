package uk.co.keyoflife.standaloan;

import jakarta.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.nio.file.*;

@Service
public class DocumentStorageService {
  private static final Logger logger = LoggerFactory.getLogger(DocumentStorageService.class);
  private final Path uploadPath = Paths.get("uploads");

  @PostConstruct
  private void init() throws IOException {
    if (!uploadPath.toFile().exists()) {
      Files.createDirectory(uploadPath);
      logger.info("Created upload directory");
    }
  }

  public Boolean store(final MultipartFile document) {
    final String originalFilename = document.getOriginalFilename();
    logger.info("Starting Storage of: " + originalFilename);

    final Path destinationPath = uploadPath.resolve(originalFilename);

    try {
      final File destinationFile = destinationPath.toFile();
      if (destinationFile.exists())
        destinationFile.delete();

      Files.copy(document.getInputStream(), destinationPath);
    } catch (IOException e) {
      return Boolean.FALSE;
    }

    return Boolean.TRUE;
  }
}
