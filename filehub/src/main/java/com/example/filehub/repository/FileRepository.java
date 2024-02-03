package com.example.filehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.filehub.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,String> {

}
