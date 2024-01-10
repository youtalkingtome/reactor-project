package org.example;


import java.util.random.RandomGenerator;

public class Task {
    private String id;
    private String taskType;

    public Task(String id, String taskType) {
        this.id = id;
        this.taskType = taskType;
    }

    public String getId()
    {
        return RandomGenerator.getDefault().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
