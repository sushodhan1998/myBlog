package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFondException;
import com.springboot.blog.respository.CommentRepository;
import com.springboot.blog.respository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository =postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postID,CommentDto commentDto) {

        Comment newComment = mapDtoToEntity(commentDto);
        //retrieve post by id
        Post response =  postRepository.findById(postID).orElseThrow(()->new ResourceNotFondException("Post","Id",postID));
        newComment.setPost(response);
        Comment resposneComment = commentRepository.save(newComment);
        return mapEntityToDto(resposneComment);
    }

    @Override
    public List<CommentDto> getAllComments(long postID) {
        List<Comment> comments = commentRepository.findByPostId(postID);
        return comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentbyId(long postId, long commentId) {
        //retrieve post by id
        Post response =  postRepository.findById(postId).orElseThrow(()->new ResourceNotFondException("Post","Id",postId));
        //retrieve comment by id
        Comment commentresponse =  commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFondException("Comment","Id",commentId));
        if(commentresponse.getPost().getId()!=response.getId())
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to particular post");

        return mapEntityToDto(commentresponse);
    }

    @Override
    public CommentDto updateCommentById(long postId,long commentId, CommentDto commentDto) {
        //retrieve post by id
        Post  response = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFondException("Post","Id",postId));
        //retrieve comment by id
        Comment commentresponse =  commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFondException("Comment","Id",commentId));

        if(commentresponse.getPost().getId()!=response.getId())
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to particular post");

        commentresponse.setName(commentDto.getName());
        commentresponse.setEmail(commentDto.getEmail());
        commentresponse.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(commentresponse);
        return mapEntityToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        //retrieve post by id
        Post response = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFondException("Post","Id",postId));
        //retrieve comment by id
        Comment commentresponse =  commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFondException("Comment","Id",commentId));

        if(commentresponse.getPost().getId()!=response.getId())
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to particular post");

        commentRepository.deleteById(commentId);
    }


    public CommentDto mapEntityToDto (Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());

        return commentDto;
    }

    public Comment mapDtoToEntity(CommentDto commentDto){
//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        return comment;
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }
}

