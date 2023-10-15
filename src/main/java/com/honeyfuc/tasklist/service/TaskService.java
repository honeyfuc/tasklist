package com.honeyfuc.tasklist.service;

import com.honeyfuc.tasklist.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUserId(Long id);

    Task update(Task task);

    Task create(Task task);

    void delete(Long id);
}
