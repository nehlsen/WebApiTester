CREATE TABLE null_assertions
(
    http_task_entity_uuid BINARY (16) NOT NULL,
    assertions_uuid BINARY (16) NOT NULL
);

CREATE TABLE plan
(
    uuid BINARY (16) NOT NULL,
    name            VARCHAR(255) NULL,
    schedule        VARCHAR(255) NULL,
    schedule_active BIT(1)       NOT NULL,
    created         datetime     NULL,
    updated         datetime     NULL,
    CONSTRAINT pk_plan PRIMARY KEY (uuid)
);

CREATE TABLE plan_execution_record
(
    uuid BINARY (16) NOT NULL,
    plan_uuid BINARY (16) NULL,
    start_time_epoch_millis BIGINT   NOT NULL,
    end_time_epoch_millis   BIGINT   NOT NULL,
    result_positive         BIT(1)   NOT NULL,
    created                 datetime NULL,
    updated                 datetime NULL,
    CONSTRAINT pk_plan_execution_record PRIMARY KEY (uuid)
);

CREATE TABLE plan_tasks
(
    plan_entity_uuid BINARY (16) NOT NULL,
    tasks_uuid BINARY (16) NOT NULL
);

CREATE TABLE t_assertion
(
    uuid BINARY (16) NOT NULL,
    dtype                       VARCHAR(31) NULL,
    created                     datetime    NULL,
    updated                     datetime    NULL,
    maximum_request_time_millis BIGINT      NOT NULL,
    expected_status_code        INT         NOT NULL,
    CONSTRAINT pk_t_assertion PRIMARY KEY (uuid)
);

CREATE TABLE task
(
    uuid BINARY (16) NOT NULL,
    dtype   VARCHAR(31)  NULL,
    name    VARCHAR(255) NULL,
    created datetime     NULL,
    updated datetime     NULL,
    uri     VARCHAR(255) NULL,
    CONSTRAINT pk_task PRIMARY KEY (uuid)
);

CREATE TABLE task_assertions
(
    task_entity_uuid BINARY (16) NOT NULL,
    assertions_uuid BINARY (16) NOT NULL
);

CREATE TABLE task_execution_record
(
    uuid BINARY (16) NOT NULL,
    plan_execution_record_uuid BINARY (16) NULL,
    task_uuid BINARY (16) NULL,
    start_time_epoch_millis BIGINT   NOT NULL,
    end_time_epoch_millis   BIGINT   NOT NULL,
    result_positive         BIT(1)   NOT NULL,
    request_uuid BINARY (16) NULL,
    response_uuid BINARY (16) NULL,
    created                 datetime NULL,
    updated                 datetime NULL,
    CONSTRAINT pk_task_execution_record PRIMARY KEY (uuid)
);

CREATE TABLE task_execution_record_request
(
    uuid BINARY (16) NOT NULL,
    method  VARCHAR(255) NULL,
    uri     VARCHAR(255) NULL,
    body    LONGTEXT     NULL,
    created datetime     NULL,
    updated datetime     NULL,
    CONSTRAINT pk_task_execution_record_request PRIMARY KEY (uuid)
);

CREATE TABLE task_execution_record_request_headers
(
    task_execution_record_request_uuid BINARY (16) NOT NULL,
    header_value BLOB         NULL,
    header_name  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_execution_record_request_headers PRIMARY KEY (task_execution_record_request_uuid, header_name)
);

CREATE TABLE task_execution_record_response
(
    uuid BINARY (16) NOT NULL,
    status_code          INT      NOT NULL,
    body                 LONGTEXT NULL,
    response_time_millis BIGINT   NOT NULL,
    created              datetime NULL,
    updated              datetime NULL,
    CONSTRAINT pk_task_execution_record_response PRIMARY KEY (uuid)
);

CREATE TABLE task_execution_record_response_headers
(
    task_execution_record_response_uuid BINARY (16) NOT NULL,
    header_value BLOB         NULL,
    header_name  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_execution_record_response_headers PRIMARY KEY (task_execution_record_response_uuid, header_name)
);

CREATE TABLE task_headers
(
    task_uuid BINARY (16) NOT NULL,
    header_values BLOB         NULL,
    header_name   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_headers PRIMARY KEY (task_uuid, header_name)
);

CREATE TABLE task_parameters
(
    task_uuid BINARY (16) NOT NULL,
    parameter_value VARCHAR(255) NULL,
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