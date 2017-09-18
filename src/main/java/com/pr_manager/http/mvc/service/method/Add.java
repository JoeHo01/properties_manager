package com.pr_manager.http.mvc.service.method;

import com.pr_manager.http.mvc.dao.PropertiesDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class Add {
    
    private final
    PropertiesDao propertiesDao;

    @Autowired
    public Add(PropertiesDao propertiesDao) {
        this.propertiesDao = propertiesDao;
    }

    public void add(String property, String key, String parentId) {
        int i = checkJSON(property);
        switch (i) {
            case 1:
                addObject(JSONObject.fromObject(property), key, parentId);
                break;
            case 2:
                addArray(JSONArray.fromObject(property), key, parentId);
                break;
            case 0:
                addValue(property, key, parentId);
                break;
        }
    }

    private int checkJSON(String words) {
        if (words.equals("null"))
            return 0;
        try {
            JSONObject.fromObject(words);
            return 1;
        } catch (Exception e) {
            try {
                JSONArray.fromObject(words);
                return 2;
            } catch (Exception e1) {
                return 0;
            }
        }
    }

    private void addObject(JSONObject property, String key, String parentId) {
        Map<String,String> param = new HashMap<>();
        param.put("id","0");
        param.put("key",key);
        param.put("parentId",parentId);
        propertiesDao.addObject(param);

        Set<String> propertyKeys = property.keySet();
        for (String propertyKey : propertyKeys) {
            String words = property.getString(propertyKey);
            add(words, propertyKey, param.get("id"));
        }
    }

    private void addArray(JSONArray property, String key, String parentId) {
        Map<String,String> param = new HashMap<>();
        param.put("id","0");
        param.put("key",key);
        param.put("parentId",parentId);
        propertiesDao.addArray(param);
        for (int i = 0; i < property.size(); i++) {
            JSONObject object = property.getJSONObject(i);
            addObject(object, key, param.get("id"));
        }
    }

    private void addValue(String value, String key, String parentId) {
        Map<String,String> param = new HashMap<>();
        param.put("value",value);
        param.put("key",key);
        param.put("parentId",parentId);
        propertiesDao.addValue(param);
    }
}
