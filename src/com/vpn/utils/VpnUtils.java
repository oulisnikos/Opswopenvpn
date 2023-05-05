/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vpn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JTextArea;

/**
 *
 * @author oulisnikos
 */
public class VpnUtils {
    public final static String connectCommand = "openvpn3 session-start --config %s";
    public static void runCommand(JTextArea textArea, String pathConfigFile) throws Exception {
//        String command = "openvpn3 session-start --config " + pathConfigFile;

        Process proc = Runtime.getRuntime().exec(String.format(connectCommand, pathConfigFile));

        // Read the output

        BufferedReader reader =  
              new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        while((line = reader.readLine()) != null) {
//            System.out.print(line + "\n");
            textArea.append(line + "\n");
        }

        proc.waitFor();
    }
    
    public static String getConnectionPath() throws Exception {
        String result = "";
        String command = "openvpn3 sessions-list";

        Process proc = Runtime.getRuntime().exec(command);

        // Read the output

        BufferedReader reader =  
              new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        while((line = reader.readLine()) != null) {
            if(line.contains("Path")) {
                String[] strs = line.split(":");
                if(strs.length == 2) {
                    result = strs[1].trim();
                }
            }
        }

        proc.waitFor();
        return result;
    }
    
    public static void removeConnection(JTextArea textArea, String connectionPath)
        throws Exception {
        if(connectionPath != null)
        {
            String command = "openvpn3 session-manage --session-path " + connectionPath + " --disconnect";

            Process proc = Runtime.getRuntime().exec(command);

            // Read the output

            BufferedReader reader =  
                  new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
    //            System.out.print(line + "\n");
                textArea.append(line + "\n");
            }

            proc.waitFor();
        }
    }
}
