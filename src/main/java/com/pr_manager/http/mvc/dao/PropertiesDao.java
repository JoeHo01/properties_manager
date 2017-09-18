package com.pr_manager.http.mvc.dao;

import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PropertiesDao {

    void addObject(Map<String,String> param);

    void addArray(Map<String,String> param);

    void addValue(Map<String,String> param);

    List<JSONObject> getSubProperties(String parentId);

    List<String> getChildrenId(String id);

    JSONObject getProperty(String id);

    String getColumnId(@Param("id") String id, @Param("propertyKey") String[] propertyKey);

    String getColumnValue(String columnId);

    int updateColumn(Map<String, String> param);
}
