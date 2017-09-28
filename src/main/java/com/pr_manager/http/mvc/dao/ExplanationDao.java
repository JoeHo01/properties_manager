package com.pr_manager.http.mvc.dao;

import com.pr_manager.http.mvc.entity.ExplanationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExplanationDao extends MongoRepository<ExplanationEntity,String> {
}
