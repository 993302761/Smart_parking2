package com.saltfish.example;

public class test {
    public static void main(String[] args) {
        String srt = "/group1/M00/00/00/wKgKbGL5_aiAZplAAAuzTPPOXv840.jpeg";
        String path = "/asd/sss/";
        if (path.charAt(path.length()-1) == '/')
            path = path.substring(0,path.length()-1);
        int index = srt.lastIndexOf("/");
        int ins = srt.indexOf("/",2);
        String filename = srt.substring(index,srt.length());
        String groupname = srt.substring(1,ins);
        String MetaPath = srt.substring(ins+1);
        System.out.println(filename);
        System.out.println(groupname);
        System.out.println(MetaPath);
        System.out.println(path);
        System.out.println(path+filename);
        String[] files = filename.split("\\.");
        String a = files[0]+"(1)"+".";
        for (int i = 1;i < files.length;i++){
            a+=files[i];
            if (i < files.length-1)
                a+=".";
        }
        System.out.println(a);
    }
}
