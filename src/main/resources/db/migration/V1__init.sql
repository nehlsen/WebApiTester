CREATE TABLE null_assertions
(
    http_task_entity_uuid UUID NOT NULL,
    assertions_uuid       UUID NOT NULL
);

CREATE TABLE plan
(
    uuid            UUID    NOT NULL,
    name            VARCHAR(255),
    schedule        VARCHAR(255),
    schedule_active BOOLEAN NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE,
    updated         TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_plan PRIMARY KEY (uuid)
);

CREATE TABLE plan_execution_record
(
    uuid                    UUID    NOT NULL,
    plan_uuid               UUID,
    start_time_epoch_millis BIGINT  NOT NULL,
    end_time_epoch_millis   BIGINT  NOT NULL,
    result_positive         BOOLEAN NOT NULL,
    created                 TIMESTAMP WITHOUT TIME ZONE,
    updated                 TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_plan_execution_record PRIMARY KEY (uuid)
);

CREATE TABLE plan_tasks
(
    plan_entity_uuid UUID NOT NULL,
    tasks_uuid       UUID NOT NULL
);

CREATE TABLE t_assertion
(
    uuid                        UUID    NOT NULL,
    dtype                       VARCHAR(31),
    created                     TIMESTAMP WITHOUT TIME ZONE,
    updated                     TIMESTAMP WITHOUT TIME ZONE,
    maximum_request_time_millis BIGINT,
    expected_status_code        INTEGER,
    CONSTRAINT pk_t_assertion PRIMARY KEY (uuid)
);

CREATE TABLE task
(
    uuid    UUID NOT NULL,
    dtype   VARCHAR(31),
    name    VARCHAR(255),
    created TIMESTAMP WITHOUT TIME ZONE,
    updated TIMESTAMP WITHOUT TIME ZONE,
    uri     BYTEA,
    CONSTRAINT pk_task PRIMARY KEY (uuid)
);

CREATE TABLE task_assertions
(
    task_entity_uuid UUID NOT NULL,
    assertions_uuid  UUID NOT NULL
);

CREATE TABLE task_execution_record
(
    uuid                       UUID    NOT NULL,
    plan_execution_record_uuid UUID,
    task_uuid                  UUID,
    start_time_epoch_millis    BIGINT  NOT NULL,
    end_time_epoch_millis      BIGINT  NOT NULL,
    result_positive            BOOLEAN NOT NULL,
    request_uuid               UUID,
    response_uuid              UUID,
    created                    TIMESTAMP WITHOUT TIME ZONE,
    updated                    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_task_execution_record PRIMARY KEY (uuid)
);

CREATE TABLE task_execution_record_request
(
    uuid    UUID NOT NULL,
    method  VARCHAR(255),
    uri     VARCHAR(255),
    body    VARCHAR(32600),
    created TIMESTAMP WITHOUT TIME ZONE,
    updated TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_task_execution_record_request PRIMARY KEY (uuid)
);

CREATE TABLE task_execution_record_request_headers
(
    task_execution_record_request_uuid UUID         NOT NULL,
    header_value VARCHAR(255) ARRAY,
    header_name                        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_execution_record_request_headers PRIMARY KEY (task_execution_record_request_uuid, header_name)
);

CREATE TABLE task_execution_record_response
(
    uuid                 UUID    NOT NULL,
    status_code          INTEGER NOT NULL,
    body                 VARCHAR(32600),
    response_time_millis BIGINT  NOT NULL,
    created              TIMESTAMP WITHOUT TIME ZONE,
    updated              TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_task_execution_record_response PRIMARY KEY (uuid)
);

CREATE TABLE task_execution_record_response_headers
(
    task_execution_record_response_uuid UUID         NOT NULL,
    header_value VARCHAR(255) ARRAY,
    header_name                         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_execution_record_response_headers PRIMARY KEY (task_execution_record_response_uuid, header_name)
);

CREATE TABLE task_headers
(
    task_uuid   UUID         NOT NULL,
    header_values VARCHAR(255) ARRAY,
    header_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_headers PRIMARY KEY (task_uuid, header_name)
);

CREATE TABLE task_parameters
(
    task_uuid       UUID         NOT NULL,
    parameter_value VARCHAR(255),
    parameter_name  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_parameters PRIMARY KEY (task_uuid, parameter_name)
);

ALTER TABLE null_assertions
    ADD CONSTRAINT uc_null_assertions_assertions_uuid UNIQUE (assertions_uuid);

ALTER TABLE plan_tasks
    ADD CONSTRAINT uc_plan_tasks_tasks_uuid UNIQUE (tasks_uuid);

ALTER TABLE task_assertions
    ADD CONSTRAINT uc_task_assertions_assertions_uuid UNIQUE (assertions_uuid);

ALTER TABLE plan_execution_record
    ADD CONSTRAINT FK_PLAN_EXECUTION_RECORD_ON_PLAN_UUID FOREIGN KEY (plan_uuid) REFERENCES plan (uuid);

ALTER TABLE task_execution_record
    ADD CONSTRAINT FK_TASK_EXECUTION_RECORD_ON_PLAN_EXECUTION_RECORD_UUID FOREIGN KEY (plan_execution_record_uuid) REFERENCES plan_execution_record (uuid);

ALTER TABLE task_execution_record
    ADD CONSTRAINT FK_TASK_EXECUTION_RECORD_ON_REQUEST_UUID FOREIGN KEY (request_uuid) REFERENCES task_execution_record_request (uuid);

ALTER TABLE task_execution_record
    ADD CONSTRAINT FK_TASK_EXECUTION_RECORD_ON_RESPONSE_UUID FOREIGN KEY (response_uuid) REFERENCES task_execution_record_response (uuid);

ALTER TABLE task_execution_record
    ADD CONSTRAINT FK_TASK_EXECUTION_RECORD_ON_TASK_UUID FOREIGN KEY (task_uuid) REFERENCES task (uuid);

ALTER TABLE null_assertions
    ADD CONSTRAINT fk_nulass_on_assertion_entity FOREIGN KEY (assertions_uuid) REFERENCES t_assertion (uuid);

ALTER TABLE null_assertions
    ADD CONSTRAINT fk_nulass_on_http_task_entity FOREIGN KEY (http_task_entity_uuid) REFERENCES task (uuid);

ALTER TABLE plan_tasks
    ADD CONSTRAINT fk_platas_on_plan_entity FOREIGN KEY (plan_entity_uuid) REFERENCES plan (uuid);

ALTER TABLE plan_tasks
    ADD CONSTRAINT fk_platas_on_task_entity FOREIGN KEY (tasks_uuid) REFERENCES task (uuid);

ALTER TABLE task_assertions
    ADD CONSTRAINT fk_tasass_on_assertion_entity FOREIGN KEY (assertions_uuid) REFERENCES t_assertion (uuid);

ALTER TABLE task_assertions
    ADD CONSTRAINT fk_tasass_on_task_entity FOREIGN KEY (task_entity_uuid) REFERENCES task (uuid);

ALTER TABLE task_execution_record_request_headers
    ADD CONSTRAINT fk_task_execution_record_request_headers_on_request FOREIGN KEY (task_execution_record_request_uuid) REFERENCES task_execution_record_request (uuid);

ALTER TABLE task_execution_record_response_headers
    ADD CONSTRAINT fk_task_execution_record_response_headers_on_response FOREIGN KEY (task_execution_record_response_uuid) REFERENCES task_execution_record_response (uuid);

ALTER TABLE task_headers
    ADD CONSTRAINT fk_task_headers_on_http_task_entity FOREIGN KEY (task_uuid) REFERENCES task (uuid);

ALTER TABLE task_parameters
    ADD CONSTRAINT fk_task_parameters_on_http_task_entity FOREIGN KEY (task_uuid) REFERENCES task (uuid);