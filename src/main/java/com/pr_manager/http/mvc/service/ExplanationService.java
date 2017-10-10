package com.pr_manager.http.mvc.service;

import com.pr_manager.common.utils.ExecUtil;
import com.pr_manager.common.utils.FileUtil;
import com.pr_manager.http.mvc.dao.ExplanationDao;
import com.pr_manager.http.mvc.entity.ExplanationEntity;
import com.pr_manager.particular.DocUtil;
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
        String doc = DocUtil.getDocWords(explanations);
        String basePath = System.getProperty("user.dir");
        boolean IF_write = FileUtil.writeFile(basePath + "/doc", "apiDoc.java", doc, false);
        if (IF_write) {
            String command = FileUtil.readFile(basePath + "/doc_conf.txt");
            ExecUtil.shellCmd(command);
            return "1";
        }
        return null;
    }
}
