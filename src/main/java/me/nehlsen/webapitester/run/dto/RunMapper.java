package me.nehlsen.webapitester.run.dto;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.task.HttpGetTaskEntity;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import me.nehlsen.webapitester.persistence.task.VoidTaskEntity;
import me.nehlsen.webapitester.persistence.task.assertion.AssertionEntity;
import me.nehlsen.webapitester.persistence.task.assertion.RequestTimeAssertionEntity;
import me.nehlsen.webapitester.persistence.task.assertion.ResponseStatusCodeAssertionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface RunMapper {

    PlanDto planEntityToDto(PlanEntity plan);

    default TaskDto taskEntityToDto(TaskEntity task) {
        if (task instanceof HttpGetTaskEntity httpGetTask) {
            return httpGetTaskEntityToDto(httpGetTask);
        }
        if (task instanceof VoidTaskEntity voidTask) {
            return voidTaskEntityToDto(voidTask);
        }

        throw new RuntimeException();
    }
    HttpGetTaskDto httpGetTaskEntityToDto(HttpGetTaskEntity task);
    VoidTaskDto voidTaskEntityToDto(VoidTaskEntity task);

    default AssertionDto assertionEntityToDto(AssertionEntity assertion) {
        if (assertion instanceof RequestTimeAssertionEntity requestTimeAssertion) {
            return requestTimeAssertionEntityToDto(requestTimeAssertion);
        }
        if (assertion instanceof ResponseStatusCodeAssertionEntity responseStatusCodeAssertion) {
            return responseStatusCodeAssertionEntityToDto(responseStatusCodeAssertion);
        }

        throw new RuntimeException();
    }
    RequestTimeAssertionDto requestTimeAssertionEntityToDto(RequestTimeAssertionEntity requestTimeAssertion);
    ResponseStatusCodeAssertionDto responseStatusCodeAssertionEntityToDto(ResponseStatusCodeAssertionEntity responseStatusCodeAssertion);
}
