package com.codeplay.codeplay_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codeplay.codeplay_api.dto.SubscriptionPlansDto;
import com.codeplay.codeplay_api.entity.SubscriptionPlans;

@Repository
public interface SubscriptionPlansRepository extends JpaRepository<SubscriptionPlans, String> {
    
}
