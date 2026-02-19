-- Drop tables if they exist (order matters due to FKs)
DROP TABLE IF EXISTS enrollment;
DROP TABLE IF EXISTS class_table;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS venue;

-- Table for Venue Management
CREATE TABLE venue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT,
    address VARCHAR(255),
    description TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for Roles
CREATE TABLE sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    role_name VARCHAR(100),
    `function` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for Users (including Lecturers)
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account VARCHAR(50) NOT NULL UNIQUE,
    user_name VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    bio TEXT,
    specialty VARCHAR(255),
    avatar_url VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for Class Schedule
CREATE TABLE class_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    sub_title VARCHAR(255),
    weekly VARCHAR(50),
    date VARCHAR(50),
    seq_no INT,
    venue_id BIGINT,
    instructor_id BIGINT,
    speaker VARCHAR(100),
    max_participants INT,
    CONSTRAINT fk_class_venue FOREIGN KEY (venue_id) REFERENCES venue(id),
    CONSTRAINT fk_class_instructor FOREIGN KEY (instructor_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for Course Enrollment
CREATE TABLE enrollment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,
    enrollment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'ENROLLED',
    CONSTRAINT fk_enrollment_user FOREIGN KEY (user_id) REFERENCES sys_user(id),
    CONSTRAINT fk_enrollment_class FOREIGN KEY (class_id) REFERENCES class_table(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
