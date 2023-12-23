package com.honeyfuc.tasklist.service.impl;

import com.honeyfuc.tasklist.config.TestConfig;
import com.honeyfuc.tasklist.domain.exception.ResourceNotFoundException;
import com.honeyfuc.tasklist.domain.task.Status;
import com.honeyfuc.tasklist.domain.task.Task;
import com.honeyfuc.tasklist.domain.task.TaskImage;
import com.honeyfuc.tasklist.repository.TaskRepository;
import com.honeyfuc.tasklist.repository.UserRepository;
import com.honeyfuc.tasklist.service.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ImageService imageService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private TaskServiceImpl taskService;

    @Test
    void getById_test() {
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(task));
        Task testTask = taskService.getById(id);
        Mockito.verify(taskRepository).findById(id);
        Assertions.assertEquals(task, testTask);
    }

    @Test
    void getById_withInvalidIdTest() {
        Long id = 1L;
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> taskService.getById(id));
        Mockito.verify(taskRepository).findById(id);
    }

    @Test
    void getAllByUserId_test() {
        Long userId = 1L;
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task());
        }
        Mockito.when(taskRepository.findAllByUserId(userId))
                .thenReturn(tasks);

        List<Task> testTasks = taskService.getAllByUserId(userId);
        Mockito.verify(taskRepository).findAllByUserId(userId);
        Assertions.assertEquals(tasks, testTasks);
    }

    @Test
    void update_test() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Description");
        task.setTitle("title");
        task.setExpirationDate(LocalDateTime.now());
        task.setStatus(Status.DONE);

        Task testTask = taskService.update(task);
        Mockito.verify(taskRepository).save(task);
        Assertions.assertEquals(task, testTask);
    }

    @Test
    void updateWithNullStatus_test() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Description");
        task.setTitle("title");
        task.setExpirationDate(LocalDateTime.now());

        Task testTask = taskService.update(task);
        Mockito.verify(taskRepository).save(task);
        Assertions.assertEquals(Status.TODO, testTask.getStatus());
    }

    @Test
    void create_test() {
        Long taskId = 1L;
        Long userId = 1L;

        Task task = new Task();
        Mockito.doAnswer(invocationOnMock -> {
            Task savedTask = invocationOnMock.getArgument(0);
            savedTask.setId(taskId);
            return savedTask;
        }).when(taskRepository).save(task);

        Task testTask = taskService.create(task, userId);
        Mockito.verify(taskRepository).save(task);
        Assertions.assertNotNull(testTask.getId());
        Mockito.verify(taskRepository).assignTask(userId, task.getId());
    }

    @Test
    void delete_test() {
        Long id = 1L;
        taskService.delete(id);
        Mockito.verify(taskRepository).deleteById(id);
    }

    @Test
    void uploadImage_test() {
        Long id = 1L;
        String imageName = "imageName";
        TaskImage taskImage = new TaskImage();
        Mockito.when(imageService.upload(taskImage))
                .thenReturn(imageName);
        taskService.uploadImage(id, taskImage);
        Mockito.verify(taskRepository).addImage(id, imageName);
    }
}
