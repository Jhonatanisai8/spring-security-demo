package com.isai.spring_security_demo;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;

import com.isai.spring_security_demo.persistens.entitys.PermissionEntity;
import com.isai.spring_security_demo.persistens.entitys.RoleEntity;
import com.isai.spring_security_demo.persistens.entitys.RoleEnum;
import com.isai.spring_security_demo.persistens.entitys.UserEntity;
import com.isai.spring_security_demo.persistens.entitys.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

	// creacion de usuarios
	@Bean
	CommandLineRunner init(UserRepository repository) {
		return args -> {

			// creacion permisos
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATED")
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();
			PermissionEntity deletepPermission = PermissionEntity.builder()
					.name("DELETE")
					.build();
			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			// creacion de roles
			RoleEntity roleAdmin = RoleEntity.builder()
					.rolEnum(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletepPermission))
					.build();
			RoleEntity roleUser = RoleEntity.builder()
					.rolEnum(RoleEnum.USER)
					.permissions(Set.of(createPermission, readPermission))
					.build();
			RoleEntity roleInvited = RoleEntity.builder()
					.rolEnum(RoleEnum.INVITED)
					.permissions(Set.of(readPermission))
					.build();
			RoleEntity roleDeveloper = RoleEntity.builder()
					.rolEnum(RoleEnum.DEVELOPER)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletepPermission,
							refactorPermission))
					.build();

			// creacion de usuarios
			UserEntity user01 = UserEntity.builder()
					.userName("jhona")
					.userPassword("1234")
					.isEnabled(true)
					.accountNoExpire(true)
					.accountNoLoked(true)
					.credentialsNoExpire(true)
					.roles(Set.of(roleAdmin))
					.build();
			UserEntity user02 = UserEntity.builder()
					.userName("eli")
					.userPassword("1234")
					.isEnabled(true)
					.accountNoExpire(true)
					.accountNoLoked(true)
					.credentialsNoExpire(true)
					.roles(Set.of(roleUser))
					.build();
			UserEntity user03 = UserEntity.builder()
					.userName("andres")
					.userPassword("1234")
					.isEnabled(true)
					.accountNoExpire(true)
					.accountNoLoked(true)
					.credentialsNoExpire(true)
					.roles(Set.of(roleInvited))
					.build();
			UserEntity user04 = UserEntity.builder()
					.userName("yani")
					.userPassword("1234")
					.isEnabled(true)
					.accountNoExpire(true)
					.accountNoLoked(true)
					.credentialsNoExpire(true)
					.roles(Set.of(roleDeveloper))
					.build();

			// guardamos en la base de datos
			repository.saveAll(List.of(user01, user02, user03, user04));
		};
	}
}
