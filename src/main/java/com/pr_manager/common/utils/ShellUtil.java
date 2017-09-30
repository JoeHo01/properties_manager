package com.pr_manager.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellUtil {

    public static String execCmd(String cmd){
        try {
            String[] ex = new String[] { "/bin/sh", "-c", cmd };
            Process ps = Runtime.getRuntime().exec(ex);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
