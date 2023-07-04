package com.sparta.newsfeed.Folder.service;

import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import com.sparta.newsfeed.Folder.entity.Folder;
import com.sparta.newsfeed.Folder.repository.FolderRepository;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    
    // 폴더 추가
    public void addFolders(List<String> folderNames, User user) {

        List<Folder> existFolderList = folderRepository.findAllByUserAndTitleIn(user,folderNames);
        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) {
            if(!isExistFolderName(folderName,existFolderList)){
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            } else {
                throw new IllegalArgumentException("폴더명이 중복되었습니다.");
            }
        }
        folderRepository.saveAll(folderList);
    }

    // 폴더 조회
    public List<FolderResponseDto> getFolders(User user){
        List<Folder> folderList = folderRepository.findAllByUser(user);
        List<FolderResponseDto> responseDtoList = new ArrayList<>();

        for(Folder folder : folderList){
            responseDtoList.add(new FolderResponseDto(folder));
        }
        return responseDtoList;
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
