package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstatnts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Create a blog Post Rest api
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse
            (
                    responseCode = "201",
                    description = "Http Status 201 CREATED"
            )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto newPost = postService.createPost(postDto);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
    //get all posts
    @Operation(
            summary = "Get all Posts REST API",
            description = "Get all Posts REST API is used to get all post from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @GetMapping()
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstatnts.DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstatnts.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstatnts.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstatnts.DEFAULT_SORT_DIR,required = false) String sortDir

    ){
       return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);

    }
   //get post by id
   @Operation(
           summary = "Get Post by id REST API",
           description = "Get Post by id  REST API is used to get a post from database"
   )
   @ApiResponse
           (
                   responseCode = "200",
                   description = "Http Status 200 SUCCESS"
           )
    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable long id){
    return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }
    //update post by id

    @Operation(
            summary = "Update Post by id REST API",
            description = "Update Post by id  REST API is used to Update a post in database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDto>updatePostById(@PathVariable long id,@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updatePostbyId(id,postDto),HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post by id REST API",
            description = "Delete Post by id  REST API is used delete a post from database"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePosstById(id);
        return ResponseEntity.ok("Successfully deleted the post:"+id);
    }

    //build get post by categoryId Rest APi
    //http://localhost:8080/api/posts/category/1

    @Operation(
            summary = "Get Post by CategoryId REST API",
            description = "Get Post by CategoryId  REST API is used to get all post from database using CategoryId"
    )
    @ApiResponse
            (
                    responseCode = "200",
                    description = "Http Status 200 SUCCESS"
            )
    @GetMapping(value = "/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postDtos);
    }

}
