package com.tsv.implementation.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsv.implementation.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByRole(String name);
}
