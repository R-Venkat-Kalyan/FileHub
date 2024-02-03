package com.example.filehub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.filehub.entity.FileEntity;
import com.example.filehub.repository.FileRepository;
import org.springframework.util.StringUtils;

@Service
public class FileService {
	
	@Autowired
	private FileRepository fir;
	
	public void saveImage(MultipartFile file) throws IOException {
        saveFile(file, "image");
    }

    public void saveAudio(MultipartFile file) throws IOException {
        saveFile(file, "audio");
    }

    public void saveVideo(MultipartFile file) throws IOException {
        saveFile(file, "video");
    }

    public void savePdf(MultipartFile file) throws IOException {
        saveFile(file, "pdf");
    }

    public void saveWord(MultipartFile file) throws IOException {
        saveFile(file, "word");
    }
    
    public void saveExcel(MultipartFile file) throws IOException {
        saveFile(file, "excel");
    }
    
    public void saveCsv(MultipartFile file) throws IOException {
        saveFile(file, "csv");
    }
    private void saveFile(MultipartFile file, String fileType) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        byte[] fileData = file.getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setType(contentType);
        fileEntity.setData(fileData);
        fir.save(fileEntity);
    }
    
    public void saveAnyFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        byte[] fileData = file.getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setType(contentType);
        fileEntity.setData(fileData);
        
        fir.save(fileEntity);
    }
    

    public FileEntity getFile(String id) {
        return fir.findById(id).orElse(null);
    }

    public Map<String, List<FileEntity>> getAllFilesByType() {
        Map<String, List<FileEntity>> filesByType = new HashMap<>();

        // Fetch all files from the database
        List<FileEntity> allFiles = fir.findAll();

        // Organize files by type
        for (FileEntity file : allFiles) {
            String fileType = getFileType(file.getName()); // Implement this method to extract file type
            filesByType.computeIfAbsent(fileType, k -> new ArrayList<>()).add(file);
        }

        return filesByType;
    }
    
    private String getFileType(String fileName) {
        // Extract file extension from the file name
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return ""; // Return empty string if no file extension found or the file name starts or ends with a dot
    }
    
    public void deleteFile(String id) {
    	fir.deleteById(id);
    }


}
