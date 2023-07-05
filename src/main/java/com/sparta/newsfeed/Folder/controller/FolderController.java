package com.sparta.newsfeed.Folder.controller;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Folder.dto.FolderRequestDto;
import com.sparta.newsfeed.Folder.dto.FolderResponseDto;
import com.sparta.newsfeed.Folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    // 폴더 생성
    @PostMapping("/folders")
    public void addFolder(@RequestBody FolderRequestDto folderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        folderService.addFolders(folderRequestDto.getFolderName(),userDetails.getUser());
    }

    // 폴더 조회(수정)
    @GetMapping("/folders/user={user_id}")
    public String getFolders(@PathVariable Long user_id, Model model){
        List<FolderResponseDto> folder = folderService.getFolders(user_id);
        model.addAttribute("folder",folder);
        return null; // 맞는 HTML 연결
    }
    
}
