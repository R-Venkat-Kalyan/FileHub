package com.example.filehub.controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.filehub.entity.FileEntity;
import com.example.filehub.service.FileService;

import jakarta.annotation.Resource;

import org.springframework.util.StringUtils;

@Controller
public class FilesController {
	
	@Autowired
	private FileService fis;

	@GetMapping("/")
	public String fileUpload() {
		return "uploadfile";
	}
	
	@PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile file) {
        try {
            fis.saveImage(file);
            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PostMapping("/upload/audio")
    public String uploadAudio(@RequestParam("audioFile") MultipartFile file) {
        try {
        	fis.saveAudio(file);
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/files";
        }
    }

    @PostMapping("/upload/video")
    public String uploadVideo(@RequestParam("videoFile") MultipartFile file) {
        try {
        	fis.saveVideo(file);
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/files";
        }
    }

    @PostMapping("/upload/pdf")
    public String uploadPdf(@RequestParam("pdfFile") MultipartFile file) {
    	try {
            fis.savePdf(file);
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/files";
        }
    }
    
    @PostMapping("/upload/word")
    public String uploadWord(@RequestParam("wordFile") MultipartFile file) {
    	try {
            fis.saveWord(file);
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/files";
        }
    }
    
    @PostMapping("/upload/excel")
    public String uploadExcel(@RequestParam("excelFile") MultipartFile file) {
    	try {
            fis.saveExcel(file);
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/files";
        }
    }
    
    @PostMapping("/upload/csv")
    public String uploadCsv(@RequestParam("csvFile") MultipartFile file) {
    	try {
            fis.saveCsv(file);
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/files";
        }
    }
    
    @PostMapping("/upload/any")
    public ResponseEntity<String> uploadAny(@RequestParam("anyFile") MultipartFile file) {
    	try {
            fis.saveAnyFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
    
    @GetMapping("/files")
    public String getAllFiles(Model model) {
        Map<String, List<FileEntity>> filesByType = fis.getAllFilesByType();
        model.addAttribute("filesByType", filesByType);
        return "retrivefile";
    }
    
    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) {
        // Retrieve the file entity from the service
        FileEntity fileEntity = fis.getFile(fileId);

        // Check if the file exists
        if (fileEntity == null) {
            return ResponseEntity.notFound().build();
        }

        // Convert byte array to a Resource object
        ByteArrayResource resource = new ByteArrayResource(fileEntity.getData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(resource);
    }
    
    @RequestMapping("/deleteFile/{id}")
	public String deleteMapping(@PathVariable("id") String id) {
			fis.deleteFile(id);
			return "redirect:/files";
		
	}
}
