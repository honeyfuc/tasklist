package com.honeyfuc.tasklist.repository.impl;

import com.honeyfuc.tasklist.domain.user.Role;
import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.repository.DataSourceConfig;
import com.honeyfuc.tasklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
                SELECT u.id AS user_id,
                       u.name AS user_name,
                       u.username AS user_username,
                       u.password AS user_password,
                       ur.role AS user_role,
                       t.id AS task_id,
                       t.title AS task_title,
                       t.description AS task_description,
                       t.status AS task_status,
                       t.expiration_date AS task_expiration_date
                FROM users u
                    LEFT JOIN users_roles ur on u.id = ur.user_id
                    LEFT JOIN users_tasks ut on u.id = ut.user_id
                    LEFT JOIN tasks t on ut.task_id = t.id
                WHERE u.id = ?
            """;

    private final String FIND_BY_USERNAME = """
                SELECT u.id AS user_id,
                   u.name AS user_name,
                   u.username AS user_username,
                   u.password AS user_password,
                   ur.role AS user_role,
                   t.id AS task_id,
                   t.title AS task_title,
                   t.description AS task_description,
                   t.status AS task_status,
                   t.expiration_date AS task_expiration_date
                FROM users u
                    LEFT JOIN users_roles ur on u.id = ur.user_id
                    LEFT JOIN users_tasks ut on u.id = ut.user_id
                    LEFT JOIN tasks t on ut.task_id = t.id
                WHERE u.username = ?
            """;

    private final String UPDATE = """
                UPDATE users
                SET name = ?, username = ? password = ?
                WHERE id = ?
            """;

    private final String CREATE = """
                INSERT INTO users (name, username, password)
                VALUES (?, ?, ?)
            """;

    private final String INSERT_USER_ROLE = """
                INSERT INTO users_roles (user_id, role)
                VALUES (?, ?)
            """;

    private final String DELETE = """
                DELETE FROM users
                WHERE id = ?
            """;

    private final String IS_TASK_OWNER = """
                SELECT exists(
                               SELECT 1
                               FROM users_tasks
                               WHERE user_id = ?
                                 AND task_id = ?
                           )
            """;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Long userId, Role role) {

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }

}
