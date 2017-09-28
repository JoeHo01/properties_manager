package com.pr_manager.http.mvc.service;

import com.pr_manager.http.mvc.dao.PropertiesDao;
import com.pr_manager.http.mvc.entity.PropertiesEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PropertiesService {
    @Autowired
    PropertiesDao propertiesDao;

    public List<Map<String,String>> get(){
        List<Map<String,String>> result = new ArrayList<>();
        List<PropertiesEntity> properties = propertiesDao.findAll();
        Set<String> nameScopes = new HashSet<>();
        for (PropertiesEntity property:properties){
            JSONObject jsonObject = property.getProperties();
            Set<String> keys = jsonObject.keySet();

            for (String name:keys){
                nameScopes.add(property.getScope()+"JOEHO"+name);
                try {
                    JSONObject object = jsonObject.getJSONObject(name);
                    getSub(nameScopes,property.getScope()+"/"+name,object);
                }catch (Exception e){
                    break;
                }

            }
        }
        for (String nameScope:nameScopes){
            Map<String,String> property = new HashMap<>();
            String[] nameScopeArray = nameScope.split("JOEHO");
            property.put("scope",nameScopeArray[0]);
            property.put("name",nameScopeArray[1]);
            property.put("description","");
            property.put("used","");
            result.add(property);
        }
        return result;
    }

    private void getSub(Set<String> nameScope,String scope,JSONObject obj){
        Set<String> keys = obj.keySet();
        for (String key:keys){
            if (key.equals("updated_time") || key.equals("sub_properties")) continue;
            try {
                JSONObject subObj = obj.getJSONObject(key);
                nameScope.add(scope+"JOEHO"+key);
                getSub(nameScope,scope+"/"+key,subObj);
            }catch (Exception e){
                try {
                    obj.getJSONArray(key);
                    nameScope.add(scope+"JOEHO"+key);
                }catch (Exception e1){}
            }
        }
    }
}
