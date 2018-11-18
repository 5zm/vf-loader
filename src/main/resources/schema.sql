-- file_info table
CREATE TABLE IF NOT EXISTS file_info (
    file_id varchar(100) PRIMARY KEY,
    file_type varchar(100) NOT NULL,
    file_name varchar(200) NOT NULL,
    file_path varchar(200) NOT NULL,
    content_type varchar(100) NOT NULL,
    content_length bigint NOT NULL,
    registered_date timestamp NOT NULL
);
