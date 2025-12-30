package com.socialgraph.analyzer.repository;

import com.socialgraph.analyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
