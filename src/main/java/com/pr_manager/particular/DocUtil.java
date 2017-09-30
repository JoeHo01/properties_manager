package com.pr_manager.particular;

import com.pr_manager.http.mvc.entity.ExplanationEntity;

import java.util.List;

public class DocUtil {

    public static String getDocWords(List<ExplanationEntity> documents){
        StringBuilder doc = new StringBuilder();
        for (ExplanationEntity explanation : documents) {
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
        return doc.toString();
    }

}
