CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE student (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    available_days VARCHAR(255),
    preferred_start_time TIME,
    preferred_end_time TIME
);

CREATE TABLE course (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE student_courses (
    student_id UUID NOT NULL,
    courses_id UUID NOT NULL,
    CONSTRAINT fk_student_courses_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_student_courses_course FOREIGN KEY (courses_id) REFERENCES course(id),
    UNIQUE (student_id, courses_id)
);

CREATE TABLE student_topic_preferences (
    student_id UUID NOT NULL,
    topic_name VARCHAR(255),
    interest_level INT,
    CONSTRAINT fk_topic_preferences_student FOREIGN KEY (student_id) REFERENCES student(id)
);

CREATE TABLE study_goal (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description TEXT,
    target_grade VARCHAR(50),
    is_achieved BOOLEAN DEFAULT FALSE
);

CREATE TABLE student_study_goals (
    student_id UUID NOT NULL,
    study_goals_id UUID NOT NULL,
    CONSTRAINT fk_student_goals_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_student_goals_goal FOREIGN KEY (study_goals_id) REFERENCES study_goal(id)
);

CREATE TABLE study_group (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE study_group_members (
    study_group_id UUID NOT NULL,
    student_id UUID NOT NULL,
    CONSTRAINT fk_group_members_group FOREIGN KEY (study_group_id) REFERENCES study_group(id),
    CONSTRAINT fk_group_members_student FOREIGN KEY (student_id) REFERENCES student(id),
    PRIMARY KEY (study_group_id, student_id)
);

CREATE TABLE matches_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id UUID NOT NULL,
    matched_student_id UUID NOT NULL,
    match_score DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_matches_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_matches_matched FOREIGN KEY (matched_student_id) REFERENCES student(id)
);
