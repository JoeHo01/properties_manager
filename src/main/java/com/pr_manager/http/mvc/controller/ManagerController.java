package com.pr_manager.http.mvc.controller;

import com.pr_manager.common.response.DataWrapper;
import com.pr_manager.common.utils.FileUtil;
import com.pr_manager.http.mvc.service.ManagerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pr_manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    /**
     * @param param property : property with JSON format
     *              environment : environment belonged to
     * @return
     */
    @RequestMapping("saveByJSON")
    public DataWrapper<Void> saveByJSON(
            @RequestParam Map<String, String> param
    ) {
        JSONObject obj = JSONObject.fromObject(param.get("property"));
        return managerService.saveByJSON(param.get("environment"), obj);
    }

    /**
     * @param param environment : environment belonged to
     * @return
     */
    @RequestMapping("saveByFile")
    public DataWrapper<Void> saveByFile(
            @RequestParam Map<String, String> param
    ) {
        String words = FileUtil.readFile("C:/Users/D1M/Desktop/properties/Local/properties_bundle.json");
        words = words.substring(1);
        JSONArray jsonArray = JSONArray.fromObject(words);
        return managerService.saveByFile(param.get("environment"), jsonArray);
    }

    /**
     * @param param environment : environment of property the column belong to
     *              name : name of property the column belong to
     * @return
     */
    @RequestMapping("getProperty")
    public DataWrapper<JSONObject> getProperty(
            @RequestParam Map<String, String> param
    ) {
        return managerService.getProperties(param);
    }

    /**
     * @param param environment : environment of property the column belong to
     *              name : name of property the column belong to
     *              property : locate the column. divide by "/".
     *              value : change to
     * @return
     */
    @RequestMapping("updateColumn")
    public DataWrapper<Void> updateColumn(
            @RequestParam Map<String, String> param
    ) {
        return managerService.updateColumn(param);
    }

    /**
     * @param param environment : environment of property the column belong to
     *              name : name of property the column belong to
     *              property : locate the column. divide by "/".
     * @return
     */
    @RequestMapping("getColumn")
    public DataWrapper<String> getColumn(
            @RequestParam Map<String, String> param
    ) {
        return managerService.getColumn(param);
    }
}
