package com.pr_manager.http.mvc.dao;

import com.pr_manager.http.mvc.entity.PropertiesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertiesDao extends MongoRepository<PropertiesEntity,String> {
}
