package com.learning.employee_service.utils;

import com.learning.employee_service.enums.EmployeeRole;
import com.learning.employee_service.enums.Permission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learning.employee_service.enums.EmployeeRole.*;
import static com.learning.employee_service.enums.Permission.*;

public class PermissionMapping {

    private static final Map<EmployeeRole, Set<Permission>> PERMISSION_MAP = Map.of(
            GRADE_1, Set.of(POST_CREATE, POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_UPDATE),
            GRADE_2, Set.of(POST_CREATE, POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_UPDATE),
            GRADE_3, Set.of(POST_CREATE, POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_UPDATE),
            GRADE_4, Set.of(POST_CREATE, POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_UPDATE),
            GRADE_5, Set.of(POST_CREATE, POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_UPDATE),
            GUEST, Set.of(POST_CREATE, POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_CREATE, USER_UPDATE),
            ADMIN, Set.of(POST_VIEW, POSTS_VIEW, POST_UPDATE, POST_DELETE, USER_CREATE, USER_UPDATE, USER_VIEW, USERS_VIEW, USER_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(EmployeeRole role){
        return PERMISSION_MAP.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
