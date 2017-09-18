package com.pr_manager.http.mvc.service;

import com.pr_manager.common.response.DataWrapper;
import com.pr_manager.common.response.ErrorCodeEnum;
import com.pr_manager.http.mvc.dao.PropertiesDao;
import com.pr_manager.http.mvc.dao.RegionBrandDao;
import com.pr_manager.http.mvc.service.method.JSONWords;
import com.pr_manager.http.mvc.service.method.Add;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ManagerService {

    private final RegionBrandDao regionBrandDao;
    private final PropertiesDao propertiesDao;
    private final Add add;
    private final JSONWords jsonWords;

    @Autowired
    public ManagerService(Add add, JSONWords jsonWords, RegionBrandDao regionBrandDao, PropertiesDao propertiesDao) {
        this.add = add;
        this.jsonWords = jsonWords;
        this.regionBrandDao = regionBrandDao;
        this.propertiesDao = propertiesDao;
    }

    public DataWrapper<Void> saveByJSON(String environment,JSONObject obj) {
        DataWrapper<Void> dataWrapper = new DataWrapper<>();
        obj.put("environment",environment);
        regionBrandDao.add(obj);

        //Set properties detail
        String property = obj.getString("properties");
        add.add(property, "properties", "0");

        //Set propertiesId to general info
        String propertiesId = regionBrandDao.getUpId();
        obj.put("propertiesId", propertiesId);
        regionBrandDao.setPropertiesId(obj);

        return dataWrapper;
    }

    public DataWrapper<Void> saveByFile(String environment,JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("environment",environment);
                saveByJSON(environment,jsonObj);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return new DataWrapper<>();
    }

    public DataWrapper<JSONObject> getProperties(Map<String,String> param) {
        DataWrapper<JSONObject> dataWrapper = new DataWrapper<>();
        JSONObject region_brand = regionBrandDao.getIntroduction(param);
        JSONObject property = propertiesDao.getProperty(region_brand.getString("propertiesId"));
        StringBuilder words = new StringBuilder("{\n");
        words.append("\tregion:\"").append(region_brand.getString("region")).append("\",\n");
        words.append("\tbrand:\"").append(region_brand.getString("brand")).append("\",\n");
        words.append("\tscope:\"").append(region_brand.getString("scope")).append("\",\n");
        words.append("\tname:\"").append(region_brand.getString("name")).append("\",\n");
        jsonWords.getJSONString(words, property);
        words.replace(words.lastIndexOf(","), words.lastIndexOf(",") + 1, "");
        words.append("}");
        JSONObject data = JSONObject.fromObject(words.toString());
        dataWrapper.setData(data);
        return dataWrapper;
    }

    public DataWrapper<Void> updateColumn(Map<String, String> param) {
        DataWrapper<Void> dataWrapper = new DataWrapper<>();
        String id = regionBrandDao.getPropertyId(param);
        String[] propertyKey = param.get("property").split("/");
        String columnId = propertiesDao.getColumnId(id, propertyKey);
        if (columnId == null) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Exist_Null);
        } else {
            String checkColumn = propertiesDao.getColumnValue(columnId);
            if (!checkColumn.equals("json") && !checkColumn.equals("array")) {
                param.put("id", columnId);
                int i = propertiesDao.updateColumn(param);
                if (i != 1) {
                    dataWrapper.setErrorCode(ErrorCodeEnum.Error);
                }
            } else {
                dataWrapper.setErrorCode(ErrorCodeEnum.Column_NotMatch);
            }
        }
        return dataWrapper;
    }

    public DataWrapper<String> getColumn(Map<String, String> param) {
        DataWrapper<String> dataWrapper = new DataWrapper<>();
        String id = regionBrandDao.getPropertyId(param);
        String[] propertyKey = param.get("property").split("/");
        String columnId = propertiesDao.getColumnId(id, propertyKey);
        if (columnId == null) {
            dataWrapper.setErrorCode(ErrorCodeEnum.Exist_Null);
        } else {
            String column = propertiesDao.getColumnValue(columnId);
            dataWrapper.setData(column);
        }
        return dataWrapper;
    }
}
