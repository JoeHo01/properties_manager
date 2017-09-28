package com.pr_manager.http.mvc.service;

import com.pr_manager.common.utils.FileUtil;
import com.pr_manager.http.mvc.dao.ExplanationDao;
import com.pr_manager.http.mvc.entity.ExplanationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExplanationService {
    @Autowired
    ExplanationDao explanationDao;

    public String getDoc() {
        List<ExplanationEntity> explanations = explanationDao.findAll();
        StringBuilder doc = new StringBuilder();
        for (ExplanationEntity explanation : explanations) {
            String scope = explanation.getScope();
            String name = explanation.getName();
            String description = explanation.getDescription();
            String used = explanation.getUsed();
            if (name.equals("sub_properties")) continue;

            doc.append("/**\n");
            String group;
            if (scope.contains("/")) {
                String[] scopeArr = scope.split("/");
                group = scopeArr[0];
                name = scopeArr[1] + ":" + name;
            } else {
                group = scope;
            }
            switch (group) {
                case "global":
                    group = "1:" + group;
                    break;
                case "region":
                    group = "2:" + group;
                    break;
                case "brand":
                    group = "3:" + group;
                    break;
                default:
                    group = "4:" + group;
                    break;
            }

            doc.append(" * @api {" + group.substring(2, group.length()
            ) + "} " + scope + " " + name + "\n");
            doc.append(" * @apiName " + name + "\n");
            doc.append(" * @apiGroup " + group + "\n");
            doc.append(" * " + "\n");
            doc.append(" * @apiParam {JSON} description " + description + "\n");
            doc.append(" * @apiParam {JSON} used " + used + "\n");
            doc.append(" * \n");
            doc.append(" */\n");
        }
        String basePath = System.getProperty("user.dir");
        boolean IF_write = FileUtil.writeFile(basePath + "/doc", "apiDoc.java", doc.toString(), false);
        if (IF_write) {
            String path = basePath + "/getDocWeb.bat";
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("cmd.exe /c start " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return doc.toString();
    }
}
