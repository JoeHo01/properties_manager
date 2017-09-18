package com.pr_manager.common.utils;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

public class FileUtil {

    public static String readFile(String path) {
        int len = 0;
        StringBuffer str = new StringBuffer("");
        File file = new File(path);
        try {
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);
            String line = null;
            while ((line = in.readLine()) != null) {
                if (len != 0) {
                    // 处理换行符的问题
                    str.append(line);
                } else {
                    str.append(line);
                }
                len++;
            }
            in.close();
            is.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str.toString();
    }

    public static boolean writeFile(String fiePath, String content, boolean append) {//append是否文件末尾追加
        try {
            File file = new File(fiePath);
            if (!file.exists()) file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, append);
            out.write(content.getBytes("utf-8"));
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createDirectory(String path) {
        File file = new File(path);
        return file.exists() || file.mkdirs();
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()){
            file.delete();
            return true;
        }
        else return false;
    }

    public static void upload(CommonsMultipartFile file, String path, String fileName) throws IOException {
        if (createDirectory(path)){
            File newFile = new File(path + fileName);
            //通过CommonsMultipartFile的方法直接写文件
            file.transferTo(newFile);
        }

    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String path, String fileName) {
        if (fileName != null) {
            String realPath = request.getServletContext().getRealPath(path);
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
