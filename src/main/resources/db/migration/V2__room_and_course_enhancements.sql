ALTER TABLE study_group
ADD COLUMN description TEXT,
ADD COLUMN capacity INT DEFAULT 4,
ADD COLUMN is_private BOOLEAN DEFAULT FALSE,
ADD COLUMN course_id UUID,
ADD COLUMN creator_id UUID,
ADD COLUMN scheduled_start_time TIMESTAMP;

ALTER TABLE study_group
ADD CONSTRAINT fk_study_group_course FOREIGN KEY (course_id) REFERENCES course(id),
ADD CONSTRAINT fk_study_group_creator FOREIGN KEY (creator_id) REFERENCES student(id);

CREATE TABLE study_group_tags (
    study_group_id UUID NOT NULL,
    tag VARCHAR(255),
    CONSTRAINT fk_group_tags_group FOREIGN KEY (study_group_id) REFERENCES study_group(id)
);

ALTER TABLE course
ADD COLUMN credit INT;
