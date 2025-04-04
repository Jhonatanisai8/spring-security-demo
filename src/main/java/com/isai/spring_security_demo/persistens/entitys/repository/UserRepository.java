package com.isai.spring_security_demo.persistens.entitys.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isai.spring_security_demo.persistens.entitys.UserEntity;

@Repository
public interface UserRepository
        extends CrudRepository<UserEntity, Long> {

}
