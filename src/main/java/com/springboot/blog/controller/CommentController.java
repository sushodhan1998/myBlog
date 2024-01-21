package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/api/posts" )
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save Comment into database"
    )
    @ApiResponse
            (
                    responseCode = "201",
                    description = "Http Status 201 CREATED"
            )
    @PostMapping(value = "/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@Valid @RequestBody CommentDto commentDto){
    return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Comments by PostId REST API",
            description = "Get all Comments by PostId REST API is used to get all Comments by PostId from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @GetMapping(value = "/{postId}/comments")
    public ResponseEntity<List<CommentDto>>getAllCommentsByPostId(@PathVariable long postId){
        return new ResponseEntity<>(commentService.getAllComments(postId),HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment by id REST API",
            description = "Get Comment by id  REST API is used to get a post from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @GetMapping(value = "/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable long postId,@PathVariable long commentId){
        return new ResponseEntity<>(commentService.getCommentbyId(postId,commentId),HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment by id REST API",
            description = "Update Comment by id  REST API is used to Update a Comment in database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @PutMapping(value = "/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>updateCommentsByID(@PathVariable long postId, @PathVariable long commentId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto),HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment by id REST API",
            description = "Delete Comment by id  REST API is used delete a Comment from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @DeleteMapping(value = "/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentsById(@PathVariable long postId,@PathVariable long commentId){

        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment for the postId:"+postId+"CommentId:"+commentId+" deleted successfully",HttpStatus.OK);
    }

}
