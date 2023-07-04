package com.sparta.newsfeed.Folder.controller;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Folder.dto.FolderRequestDto;
import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import com.sparta.newsfeed.Folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    // 폴더 추가
    @PostMapping("/folder")
    public void addFolder(@RequestBody FolderRequestDto folderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String folderName = folderRequestDto.getFolderName();
        folderService.addFolders(folderName,userDetails.getUser());
    }

    // 폴더 조회
    @GetMapping("/folder")
    public List<FolderResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return folderService.getFolders(userDetails.getUser());
    }

}
