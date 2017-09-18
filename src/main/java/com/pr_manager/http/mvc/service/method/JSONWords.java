package com.pr_manager.http.mvc.service.method;

import com.pr_manager.http.mvc.dao.PropertiesDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JSONWords {
    @Autowired
    PropertiesDao propertiesDao;

    int deep = 0;

    public String getJSONString(StringBuilder words,JSONObject property) {
        build(words, property);
        return words.toString();
    }

    private void build(StringBuilder words, JSONObject property) {
        deep++;
        switch (property.getString("value")) {
            case "json":
                buildJSON(words,property);
                break;
            case "array":
                buildArray(words,property);
                break;
            default:
                buildValue(words, property);
                break;
        }
        deep--;
    }

    private void form(StringBuilder words) {
        for (int i = 0; i < deep; i++) {
            words.append("\t");
        }
    }

    private void buildValue(StringBuilder words, JSONObject property) {
        form(words);
        String name = property.getString("name");
        String value = property.getString("value");

        if (name.equals("updated_time")) {
            words.append(name + ":'" + value + "',\n");
        } else {
            words.append(name + ":\"" + value + "\",\n");
        }
    }

    private void buildJSON(StringBuilder words, JSONObject property) {
        String name = property.getString("name");
        String parentId = property.getString("id");

        form(words);
        words.append(name + ":{\n");

        List<JSONObject> children = propertiesDao.getSubProperties(parentId);
        for (JSONObject child : children) {
            build(words, child);
        }
        words.replace(words.lastIndexOf(","), words.lastIndexOf(",") + 1, "");
        form(words);
        words.append("},\n");
    }

    private void buildArrayJSON(StringBuilder words, JSONObject property) {
        String parentId = property.getString("id");
        form(words);
        words.append(":{\n");

        List<JSONObject> children = propertiesDao.getSubProperties(parentId);
        for (JSONObject child : children) {
            build(words, child);
        }
        words.replace(words.lastIndexOf(","), words.lastIndexOf(",") + 1, "");
        form(words);
        words.append("},\n");
    }

    private void buildArray(StringBuilder words, JSONObject property) {
        String name = property.getString("name");
        String parentId = property.getString("id");

        deep++;
        form(words);
        words.append(name + ":[\n");
        deep--;

        List<String> childrenId = propertiesDao.getChildrenId(parentId);
        for (String childId : childrenId) {
            List<JSONObject> children = propertiesDao.getSubProperties(childId);
            for (JSONObject child : children){
                buildArrayJSON(words,child);
            }
        }

        deep++;
        form(words);
        words.append("],\n");
        deep--;
    }
}
