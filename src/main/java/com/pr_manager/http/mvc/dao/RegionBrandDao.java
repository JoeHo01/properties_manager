package com.pr_manager.http.mvc.dao;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface RegionBrandDao {

    void add(Map<String, String> basic);

    String getUpId();

    void setPropertiesId(Map<String,String> propertiesId);

    JSONObject getIntroduction(Map<String, String> param);

    String getPropertyId(Map<String, String> param);
}
