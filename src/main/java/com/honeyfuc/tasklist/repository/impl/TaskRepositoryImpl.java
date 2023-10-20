package com.honeyfuc.tasklist.repository.impl;

import com.honeyfuc.tasklist.domain.exception.ResourceMappingException;
import com.honeyfuc.tasklist.domain.task.Task;
import com.honeyfuc.tasklist.repository.DataSourceConfig;
import com.honeyfuc.tasklist.repository.TaskRepository;
import com.honeyfuc.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
                SELECT t.id AS task_id,
                       t.title AS task_title,
                       t.description AS task_description,
                       t.status AS task_status,
                       t.expiration_date AS task_expiration_date,
                FROM tasks t
                WHERE id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
                SELECT t.id AS task_id,
                       t.title AS task_title,
                       t.description AS task_description,
                        t.status AS task_status,
                       t.expiration_date AS task_expiration_date,
                FROM tasks t
                JOIN users_tasks ut on t.id = ut.task_id
                WHERE ut.user_id = ?
            """;

    private final String ASSIGN = """
                INSERT INTO users_tasks (user_id, task_id)
                VALUES (?, ?)
            """;

    private final String UPDATE = """
                UPDATE tasks
                SET title = ?, description = ?, status = ?, expiration_date = ?
                WHERE id = ?
            """;

    private final String CREATE = """
                INSERT INTO tasks (title, description, status, expiration_date)
                VALUES (?, ?, ?, ?)
            """;

    private final String DELETE = """
                DELETE FROM tasks
                WHERE id = ?
            """;

    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(resultSet));
            }
        } catch (SQLException exception) {
            throw new ResourceMappingException("Error while finding task by id");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return TaskRowMapper.mapRows(resultSet);
            }
        } catch (SQLException exception) {
            throw new ResourceMappingException("Error while finding all tasks by id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(Long id) {

    }

}
