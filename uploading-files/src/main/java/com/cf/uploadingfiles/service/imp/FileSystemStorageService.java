package com.cf.uploadingfiles.service.imp;

import com.cf.uploadingfiles.config.StorageProperties;
import com.cf.uploadingfiles.exception.StorageException;
import com.cf.uploadingfiles.exception.StorageFileNotFoundException;
import com.cf.uploadingfiles.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author ：fengchen
 * @date ：Created in 2019/8/6 10:17
 * @description：文件上传
 * @version: 1.0.0
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    /**
     * 创建上传目录
     */
    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
            //Files.createDirectory(Paths.get("upload/ExcelStencil"));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    /**
     *  保存文件
     * @param file
     */
    @Override
    public void store(MultipartFile file) {
        if (file.isEmpty()){
            throw new StorageException("文件为空： " + file.getOriginalFilename());
        }
        try {
            Files.copy(file.getInputStream(),this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("上传文件失败： " + file.getOriginalFilename(), e);
        }
    }

    /**
     * 读取当前所有文件
     * @return
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * 下载文件
     * @param filename
     * @return
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
