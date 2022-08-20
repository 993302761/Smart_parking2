package com.saltfish.example;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

public class test {
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static FileItem getMultipartFile(File file, String fieldName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), true, file.getName());
        int bytesRead = 0;
        int len = 8192;
        byte[] buffer = new byte[len];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os =  item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }



    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/home/saltfish/图片/111.png");
        FileItem fileItem = getMultipartFile(file, "111.png");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println(multipartFile.getName());
        System.out.println(multipartFile.getOriginalFilename());
//        String srt = "/group1/M00/00/00/wKgKbGL5_aiAZplAAAuzTPPOXv840.jpeg";
//        String path = "/asd/sss/";
//        if (path.charAt(path.length()-1) == '/')
//            path = path.substring(0,path.length()-1);
//        int index = srt.lastIndexOf("/");
//        int ins = srt.indexOf("/",2);
//        String filename = srt.substring(index,srt.length());
//        String groupname = srt.substring(1,ins);
//        String MetaPath = srt.substring(ins+1);
//        System.out.println(filename);
//        System.out.println(groupname);
//        System.out.println(MetaPath);
//        System.out.println(path);
//        System.out.println(path+filename);
//        String[] files = filename.split("\\.");
//        String a = files[0]+"(1)"+".";
//        for (int i = 1;i < files.length;i++){
//            a+=files[i];
//            if (i < files.length-1)
//                a+=".";
//        }
//        System.out.println(a);





    }
}
