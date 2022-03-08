package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.entities.Image;

public interface ImageRepository extends CrudRepository<Image, Integer>{

}
