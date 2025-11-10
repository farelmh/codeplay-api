package com.codeplay.codeplay_api.repository;

import com.codeplay.codeplay_api.entity.Materi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriRepository extends JpaRepository<Materi, String> {}
