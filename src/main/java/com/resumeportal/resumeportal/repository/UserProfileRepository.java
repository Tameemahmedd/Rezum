package com.resumeportal.resumeportal.repository;


import com.resumeportal.resumeportal.models.User;
import com.resumeportal.resumeportal.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
    Optional<UserProfile> findByUserName(String userName);
}