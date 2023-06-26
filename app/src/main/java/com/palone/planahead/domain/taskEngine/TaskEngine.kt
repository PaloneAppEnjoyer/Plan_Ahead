package com.palone.planahead.domain.taskEngine

import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.task.Task
import com.palone.planahead.services.alarms.AlarmsHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class TaskEngine(
    private val taskRepository: TaskRepository,
    private val alarmsHandler: AlarmsHandler,
    private val alertRepository: AlertRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun completeTask(task: Task) {
        scope.launch {
            taskRepository.upsert(task.copy(isCompleted = true))
            alarmsHandler.setAlarms(taskRepository.allTasksWithAlerts.last()) // load changes
        }
    }
}