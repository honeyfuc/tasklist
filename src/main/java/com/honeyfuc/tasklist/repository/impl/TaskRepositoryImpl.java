package com.honeyfuc.tasklist.repository.impl;

import com.honeyfuc.tasklist.domain.exception.ResourceMappingException;
import com.honeyfuc.tasklist.domain.task.Task;
import com.honeyfuc.tasklist.repository.DataSourceConfig;
import com.honeyfuc.tasklist.repository.TaskRepository;
import com.honeyfuc.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                       t.expiration_date AS task_expiration_date
                FROM tasks t
                WHERE id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
                SELECT t.id AS task_id,
                       t.title AS task_title,
                       t.description AS task_description,
                       t.status AS task_status,
                       t.expiration_date AS task_expiration_date
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
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new ResourceMappingException("Error while assigning a task to a user");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            statement.setString(3, task.getStatus().name());
            if (task.getExpirationDate() == null) {
                statement.setNull(4, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()));
            }
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new ResourceMappingException("Error while updating a task");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            statement.setString(3, task.getStatus().name());
            if (task.getExpirationDate() == null) {
                statement.setNull(4, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()));
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                task.setId(resultSet.getLong(1));
            }
        } catch (SQLException exception) {
            throw new ResourceMappingException("Error while updating a task");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new ResourceMappingException("Error while deleting a task");
        }
    }

}
