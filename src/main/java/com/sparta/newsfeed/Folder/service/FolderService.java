package com.sparta.newsfeed.Folder.service;

import com.sparta.newsfeed.Folder.dto.FolderRequestDto;
import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import com.sparta.newsfeed.Folder.entity.Folder;
import com.sparta.newsfeed.Folder.repository.FolderRepository;
import com.sparta.newsfeed.User.entity.User;
import com.sparta.newsfeed.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    
    // 폴더 추가
    public void addFolders(FolderRequestDto requestDto, User user) { // 폴더이름 1개와 유저 정보

//        List<Folder> existFolderList = folderRepository.findAllByUser(user);

//        if(!isExistFolderName(folderName,existFolderList)){
            Folder folder = new Folder(requestDto, user);
            folderRepository.save(folder);
//        } else {
//            throw new IllegalArgumentException("폴더명이 중복되었습니다.");
//        }
    }

    // 폴더 조회
    @Transactional(readOnly = true)
    public List<FolderResponseDto> getFolders(Long user_id){
        return folderRepository.findAllByUserId(user_id).stream().map(FolderResponseDto::new).toList();
    }

    // DB에 있는 데이터인지 확인
    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
        for(Folder existFolder : existFolderList){
            if(folderName.equals(existFolder.getTitle())){
                return true;
            }
        }
        return false;
    }
}
