package com.palone.planahead.screens.taskEdit

import androidx.lifecycle.ViewModel
import com.palone.planahead.data.database.AlertRepository
import com.palone.planahead.data.database.TaskRepository
import com.palone.planahead.data.database.task.properties.TaskType
import com.palone.planahead.screens.taskEdit.data.AlertFieldProperty
import com.palone.planahead.screens.taskEdit.data.TaskEditUIState
import com.palone.planahead.screens.taskEdit.data.TaskRepeatMode
import com.palone.planahead.screens.taskEdit.data.typeFieldProperties.ChoreProperties
import com.palone.planahead.screens.taskEdit.data.typeFieldProperties.CronProperties
import com.palone.planahead.screens.taskEdit.data.typeFieldProperties.OneTimeProperties
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class TaskEditViewModel(
    private val taskRepository: TaskRepository,
    private val alertRepository: AlertRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(TaskEditUIState())
    val uiState: StateFlow<TaskEditUIState> = _uiState.asStateFlow()

    private val _taskType =
        MutableStateFlow(TaskType.ONE_TIME)
    val taskType: StateFlow<TaskType> = _taskType.asStateFlow()

    private val _oneTimeProperties =
        MutableStateFlow(OneTimeProperties())
    val oneTimeProperties: StateFlow<OneTimeProperties> = _oneTimeProperties.asStateFlow()

    private val _choreProperties =
        MutableStateFlow(ChoreProperties())
    val choreProperties: StateFlow<ChoreProperties> = _choreProperties.asStateFlow()

    private val _cronProperties =
        MutableStateFlow(CronProperties())
    val cronProperties: StateFlow<CronProperties> = _cronProperties.asStateFlow()

    private val _alertProperties =
        MutableStateFlow(listOf<AlertFieldProperty>())
    val alertProperties: StateFlow<List<AlertFieldProperty>> = _alertProperties.asStateFlow()


    fun insertAlertProperty(alert: AlertFieldProperty) {
        _alertProperties.update { _alertProperties.value + listOf(alert) }
    }

    fun deleteAlertProperty(alert: AlertFieldProperty) {
        _alertProperties.update { _alertProperties.value.filter { it != alert } }
    }

    fun deleteAlertProperty(index: Int) {
        _alertProperties.update { _alertProperties.value.filterIndexed { i, _ -> i != index } }
    }

    fun editAlertProperty(index: Int, alert: AlertFieldProperty) {
        val mList = _alertProperties.value.toMutableList()
        mList[index] = alert
        _alertProperties.update { mList }
    }


    fun updateTaskType(type: TaskType) {
        _taskType.update { type }
    }

    fun updateIntervalValue(value: Int) {
        _choreProperties.update { _choreProperties.value.copy(intervalValue = value) }
    }

    fun updateIntervalUnit(type: ChronoUnit) {
        _choreProperties.update { _choreProperties.value.copy(intervalType = type) }
    }

    fun updateTaskRepeatMode(mode: TaskRepeatMode) {
        _cronProperties.update { _cronProperties.value.copy(repeatMode = mode) }
    }

    fun updateDaysOfWeek(days: List<DayOfWeek>) {
        _cronProperties.update { _cronProperties.value.copy(daysOfWeek = days) }
    }

    fun updateRepeatTime(time: LocalTime) {
        _cronProperties.update { _cronProperties.value.copy(repeatTime = time) }
    }

    fun updateDateAndTime(dateAndTime: LocalDateTime) {
        _oneTimeProperties.update { _oneTimeProperties.value.copy(date = dateAndTime) }
        _choreProperties.update { _choreProperties.value.copy(date = dateAndTime) }
    }


}