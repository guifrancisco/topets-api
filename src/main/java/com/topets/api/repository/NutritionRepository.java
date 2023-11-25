package com.topets.api.repository;

import com.topets.api.domain.entity.Nutrition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutritionRepository extends MongoRepository<Nutrition, String> {
}
