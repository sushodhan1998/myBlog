package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFondException;
import com.springboot.blog.respository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository,ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper =  mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto,Category.class);
        Category response =   categoryRepository.save(category);
        return mapper.map(response,CategoryDto.class);

    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFondException("Category","id",id));
        return mapper.map(category,CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        return categories.stream().map(category -> mapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFondException("Category","id",id));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(id);

        Category updatedCategory =  categoryRepository.save(category);

        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFondException("Category","id",id));
        categoryRepository.delete(category);
    }
}
