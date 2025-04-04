package com.isai.spring_security_demo.persistens.entitys.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isai.spring_security_demo.persistens.entitys.UserEntity;

@Repository
public interface UserRepository
        extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUserName(String userName);

    // @Query("Select u from UserEntity u where u.userName = ?")
    // Optional<UserEntity> findUser(String userName);
}
