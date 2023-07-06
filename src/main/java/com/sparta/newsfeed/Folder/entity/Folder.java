package com.sparta.newsfeed.Folder.entity;

import com.sparta.newsfeed.Folder.dto.FolderRequestDto;
import com.sparta.newsfeed.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="folder")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // user 연관
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Folder(FolderRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.user = user;
    }






}
