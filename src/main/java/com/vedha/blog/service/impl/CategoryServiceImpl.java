package com.vedha.blog.service.impl;

import com.vedha.blog.dto.CategoryDto;
import com.vedha.blog.entity.CategoryEntity;
import com.vedha.blog.exception.ResourceNotFoundException;
import com.vedha.blog.repository.CategoryRepository;
import com.vedha.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        CategoryEntity category = modelMapper.map(categoryDto, CategoryEntity.class);

        CategoryEntity save = categoryRepository.save(category);

        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<CategoryEntity> all = categoryRepository.findAll();

        return all.stream().map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));

        category.setId(categoryId);
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        CategoryEntity save = categoryRepository.save(category);

        return modelMapper.map(save, CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId.toString()));

        categoryRepository.deleteById(categoryId);

        return "Deleted Successfully CategoryId : " + categoryId;
    }
}
