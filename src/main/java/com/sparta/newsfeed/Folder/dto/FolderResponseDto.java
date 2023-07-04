package com.sparta.newsfeed.Folder.dto;

import com.sparta.newsfeed.Folder.entity.Folder;

public class FolderResponseDto {
    private Long id;
    private String title;

    public FolderResponseDto(Folder folder){
        this.id = folder.getId();
        this.title = folder.getTitle();
    }
}
