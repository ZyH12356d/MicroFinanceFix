package com.sme.repository;

import com.sme.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository  extends JpaRepository<Permission,Long> {
}
