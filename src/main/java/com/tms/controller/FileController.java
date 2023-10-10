package com.tms.controller;

import com.tms.ErrorResponse;
import com.tms.exception.NotAuthorizedException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
    public class FileController {
        private final Path ROOT_FILE_PATH = Paths.get("data");

        @PostMapping("/upload")
        public ResponseEntity<HttpStatus> upload(@RequestParam("file") MultipartFile file) {
            try {
                Files.copy(file.getInputStream(), this.ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (IOException e) {
                System.out.println(e);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        @GetMapping("/{filename}")
        public ResponseEntity<Resource> getFile(@PathVariable String filename) {
            Path path = ROOT_FILE_PATH.resolve(filename);
            try {
                Resource resource = new UrlResource(path.toUri());

                if (resource.exists() || resource.isReadable()) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
                    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        @GetMapping
        public ResponseEntity<ArrayList<String>> getFiles() {
            try {
                ArrayList<String> filenames = (ArrayList<String>) Files
                        .walk(this.ROOT_FILE_PATH, 1)
                        .filter(path -> !path.equals(this.ROOT_FILE_PATH))
                        .map(Path::toString).collect(Collectors.toList());
                return new ResponseEntity<>(filenames, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }

        @DeleteMapping("/{filename}")
        public ResponseEntity<HttpStatus> deleteFile(@PathVariable String filename) {
            Path path = ROOT_FILE_PATH.resolve(filename);

            File file = new File(path.toString());
            if (file.delete()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler (NotAuthorizedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    }
