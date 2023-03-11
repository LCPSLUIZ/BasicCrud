package com.AB3.API.AB3.API.Repository;

import com.AB3.API.AB3.API.Model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, UUID> {

    List<UserInfo> findByphoneNumber(String phoneNumber);

    boolean existsByphoneNumber(String phoneNumber);

    boolean existsByID(UUID Id);

}
