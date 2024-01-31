package com.honeyfuc.tasklist.service;

import com.honeyfuc.tasklist.domain.user.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    User create(User user);

    User getTaskAuthor(Long taskId);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);
}
