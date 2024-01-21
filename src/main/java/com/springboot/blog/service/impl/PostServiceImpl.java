package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFondException;
import com.springboot.blog.respository.CategoryRepository;
import com.springboot.blog.respository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFondException("Category","CategoryId", postDto.getCategoryId()));
        //Convert DTO to entity
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        Post post = mapDtoToEntity(postDto);
        post.setCategory(category);
        //Save
        Post newPost = postRepository.save(post);

        //Convert result entity to result DTO
//        PostDto postResponse = new PostDto();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setContent(newPost.getContent());
//        postResponse.setDescription(newPost.getDescription());
        return mapEntityToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        PageRequest pageable =  PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
//        List<PostDto> responseAllPost = new ArrayList<>();
//        for(Post post:allPosts)
//        {
//            responseAllPost.add(mapEntityToDto(post));
//        }
        List<Post> allPosts = posts.getContent();

        List<PostDto> result = allPosts.stream().map(post->mapEntityToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(result);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPosts((int) posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id)  {
      Post response =  postRepository.findById(id).orElseThrow(()->new ResourceNotFondException("Post","Id",id));
      return mapEntityToDto(response);

    }

    @Override
    public PostDto updatePostbyId(long id,PostDto postDto) {
        Post result = postRepository.findById(id).orElseThrow(()->new ResourceNotFondException("Post","Id",id));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFondException("Category","CategoryId", postDto.getCategoryId()));

        Post updatePayload = mapDtoToEntity(postDto);

        result.setTitle(updatePayload.getTitle());
        result.setDescription(updatePayload.getDescription());
        result.setContent(updatePayload.getContent());
        result.setCategory(category);

        return mapEntityToDto(postRepository.save(result));

    }

    @Override
    public void deletePosstById(long id) {
        Post result = postRepository.findById(id).orElseThrow(()->new ResourceNotFondException("Post","Id",id));
        postRepository.delete(result);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFondException("Category","CategoryId",categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(post -> mapEntityToDto(post)).collect(Collectors.toList());

    }


    private Post mapDtoToEntity(PostDto postDto){
        Post post = mapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        return post;
        return post;
    }

    private PostDto mapEntityToDto(Post post){

        PostDto postDto = mapper.map(post,PostDto.class);

//        PostDto postResponse = new PostDto();
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setContent(post.getContent());
//        postResponse.setDescription(post.getDescription());
//        return postResponse;
        return postDto;
    }
}
