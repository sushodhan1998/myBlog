package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postID,CommentDto commentDto);
    public List<CommentDto> getAllComments(long postID);
    public CommentDto getCommentbyId(long postId,long commentId);

    public CommentDto updateCommentById(long postID,long commentId, CommentDto commentDto);

    public void deleteCommentById(long postId,long commentId);
}
