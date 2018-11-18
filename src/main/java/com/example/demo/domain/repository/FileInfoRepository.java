package com.example.demo.domain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.common.DemoSystemException;
import com.example.demo.domain.model.FileInfo;

@PropertySource(value = "classpath:sql/FileInfoRepository.properties")
@Repository
public class FileInfoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileInfoRepository.class);
    
    @Value("${FileInfoReppsitory.INSERT_SQL}")
    String INSERT_SQL;
    
    @Value("${FileInfoReppsitory.DELETE_BY_KEY_SQL}")
    String DELETE_BY_KEY_SQL;
    
    @Value("${FileInfoReppsitory.UPDATE_BY_KEY_SQL}")
    String UPDATE_BY_KEY_SQL;
    
    @Value("${FileInfoReppsitory.FIND_ONE_SQL}")
    String FIND_ONE_SQL;
    
    @Value("${FileInfoReppsitory.FIND_ALL_SQL}")
    String FIND_ALL_SQL;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public int insert(FileInfo fileInfo) {
        try {
            SqlParameterSource param = new BeanPropertySqlParameterSource(fileInfo);
            return jdbcTemplate.update(INSERT_SQL, param);
        } catch(DataAccessException e) {
            LOGGER.error("DataAccessError : INSERT_SQL param:{}, error:{}", fileInfo, e);
            throw new DemoSystemException("DataAccessError : FileInfoRepository INSERT_SQL", e);
        }
    }

    public int deleteByKey(String fileId) {
        try {
            SqlParameterSource param = new MapSqlParameterSource().addValue("fileId", fileId);
            return jdbcTemplate.update(DELETE_BY_KEY_SQL, param);
        } catch(DataAccessException e) {
            LOGGER.error("DataAccessError : DELETE_BY_KEY_SQL param:{}, error:{}", fileId, e);
            throw new DemoSystemException("DataAccessError : FileInfoRepository DELETE_BY_KEY_SQL", e);
        }
    }

    public int updateByKey(FileInfo fileInfo) {
        try {
            SqlParameterSource param = new BeanPropertySqlParameterSource(fileInfo);
            return jdbcTemplate.update(UPDATE_BY_KEY_SQL, param);
        } catch(DataAccessException e) {
            LOGGER.error("DataAccessError : UPDATE_BY_KEY_SQL param:{}, error:{}", fileInfo, e);
            throw new DemoSystemException("DataAccessError : FileInfoRepository UPDATE_BY_KEY_SQL", e);
        }
    }

    public FileInfo fineOne(String fileId) {
        try {
            SqlParameterSource param = new MapSqlParameterSource().addValue("fileId", fileId);
            return jdbcTemplate.queryForObject(FIND_ONE_SQL, param, fileInfoRowMapper());
        } catch(EmptyResultDataAccessException e) {
            return null;
        } catch(DataAccessException e) {
            LOGGER.error("DataAccessError : FIND_ONE_SQL param:{}, error:{}", fileId, e);
            throw new DemoSystemException("DataAccessError : FileInfoRepository FIND_ONE_SQL", e);
        }
    }

    public List<FileInfo> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_SQL, fileInfoRowMapper());
        } catch(EmptyResultDataAccessException e) {
            return null;
        } catch(DataAccessException e) {
            LOGGER.error("DataAccessError : FIND_ALL_SQL error:{}", e);
            throw new DemoSystemException("DataAccessError : FileInfoRepository FIND_ALL_SQL", e);
        }
    }

    private RowMapper<FileInfo> fileInfoRowMapper() {

        return new RowMapper<FileInfo>() {
            @Override
            public FileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                
                FileInfo fileInfo = new FileInfo();
                fileInfo.setContentLength(rs.getLong("content_length"));
                fileInfo.setContentType(rs.getString("content_type"));
                fileInfo.setFileId(rs.getString("file_id"));
                fileInfo.setFileName(rs.getString("file_name"));
                fileInfo.setFilePath(rs.getString("file_path"));
                fileInfo.setFileType(rs.getString("file_type"));
                fileInfo.setRegisteredDate(rs.getTimestamp("registered_date"));
                return fileInfo;
            }
        };
    }
}
