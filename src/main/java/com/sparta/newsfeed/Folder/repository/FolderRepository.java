package com.sparta.newsfeed.Folder.repository;

import com.sparta.newsfeed.Folder.entity.Folder;
import com.sparta.newsfeed.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;


public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByUser(User user);


    List<Folder> findAllByUserId(Long userId);
}
