package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.exception.internal_server_error.FailedCreatingArchiveException;
import com.example.hits2025_java_missed_classes.mapper.MissRequestTypeMapper;
import com.example.hits2025_java_missed_classes.model.ConfirmationFile;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.model.ArchiveModel;
import com.example.hits2025_java_missed_classes.repository.ConfirmationFileRepository;
import com.example.hits2025_java_missed_classes.repository.MissRequestsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ConfirmationFileService {

    private final ConfirmationFileRepository confirmationFileRepository;
    private final MissRequestsRepository missRequestsRepository;
    private final MissRequestTypeMapper missRequestTypeMapper;

    public ConfirmationFileService(ConfirmationFileRepository confirmationFileRepository, MissRequestsRepository missRequestsRepository, MissRequestTypeMapper missRequestTypeMapper) {
        this.confirmationFileRepository = confirmationFileRepository;
        this.missRequestsRepository = missRequestsRepository;
        this.missRequestTypeMapper = missRequestTypeMapper;
    }

    public ArchiveModel exportArchivedAttachmentsById(UUID id) {
        ConfirmationFile confirmationFile = confirmationFileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Confirmation file not found with id: " + id));

        Map<String, byte[]> fileHierarchy = Map.of(confirmationFile.getName(), confirmationFile.getData());
        byte[] archiveData = createArchive(fileHierarchy);
        return new ArchiveModel(archiveData, generateRequestDirectoryName(confirmationFile.getMissRequest()));
    }

    @Transactional
    public ArchiveModel exportArchivedAttachmentsByRequestsIds(List<UUID> ids) {
        Map<String, byte[]> fileHierarchy = new HashMap<>();

        for (UUID id : ids) {
            Optional<MissRequest> request = Optional.ofNullable(confirmationFileRepository.getMissRequestById(id).orElseThrow(() -> new EntityNotFoundException("Miss request not found with id: " + id)));

            String requestDirectoryName = generateRequestDirectoryName(request.get());
            for (ConfirmationFile file : request.get().getConfirmationFiles()) {
                fileHierarchy.put(requestDirectoryName + '/' + file.getName(), file.getData());
            }
        }

        byte[] archiveData = createArchive(fileHierarchy);
        return new ArchiveModel(archiveData, "Сводка на " + LocalDate.now());
    }

    private String generateRequestDirectoryName(MissRequest request) {
        User creator = request.getCreator();
        return creator.getSurname() + creator.getSurname().charAt(0) + creator.getPatronymic().charAt(0) + '_' + missRequestTypeMapper.toRuString(request.getType()) + '_' + request.getStartDate() + '_' + request.getEndDate();
    }

    private byte[] createArchive(Map<String, byte[]> fileHierarchy) {
        try {
            Path tempDir = createTempDirectory(fileHierarchy);
            ByteArrayOutputStream byteArrayOutputStream = archiveDirectory(tempDir);
            removeTempDirectory(tempDir);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FailedCreatingArchiveException();
        }
    }

    private static Path createTempDirectory(Map<String, byte[]> fileHierarchy) throws IOException {
        Path tempDir = Files.createTempDirectory("archive");
        createTempHierarchy(fileHierarchy, tempDir);
        return tempDir;
    }

    private static ByteArrayOutputStream archiveDirectory(Path tempDir) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            Files.walk(tempDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                try {
                    String entryName = tempDir.relativize(path).toString();
                    zipOutputStream.putNextEntry(new ZipEntry(entryName));
                    Files.copy(path, zipOutputStream);
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException("Failed to add file to archive", e);
                }
            });
        }
        return byteArrayOutputStream;
    }

    private static void createTempHierarchy(Map<String, byte[]> fileHierarchy, Path tempDir) throws IOException {
        for (Map.Entry<String, byte[]> entry : fileHierarchy.entrySet()) {
            String filePath = entry.getKey();
            byte[] fileData = entry.getValue();

            Path file = tempDir.resolve(filePath);
            Files.createDirectories(file.getParent());
            Files.write(file, fileData);
        }
    }

    private static void removeTempDirectory(Path tempDir) throws IOException {
        Files.walk(tempDir).sorted(Comparator.reverseOrder()).forEach(path -> {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete temporary file", e);
            }
        });
    }
}