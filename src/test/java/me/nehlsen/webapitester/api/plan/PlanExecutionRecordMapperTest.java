package me.nehlsen.webapitester.api.plan;

import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanExecutionRecordMapperTest {

    private PlanExecutionRecordMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PlanExecutionRecordMapper.class);
    }

    @Test
    public void it_maps_execution_record_entity_to_dto() {
        final PlanExecutionRecordEntity entity = new PlanExecutionRecordEntity();
        entity.setUuid(UUID.fromString("673b2ec6-4325-456a-bf52-2693e3c4428a"));
        entity.setStartTimeEpochMillis(123000);
        entity.setEndTimeEpochMillis(123123);
        entity.setResultPositive(true);
        entity.setUpdated(new Date());

        final PlanExecutionRecordDto dto = mapper.planExecutionRecordEntityToDto(entity);

        assertThat(dto.getUuid()).isEqualTo(entity.getUuid().toString());
        assertThat(dto.getRuntimeMillis()).isEqualTo(123);
        assertThat(dto.isResultPositive()).isEqualTo(entity.isResultPositive());
        assertThat(dto.getTimestamp()).isEqualTo(entity.getUpdated());
    }
}
