package com.cnpm.workingspace.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathUtils {
    private static final String REGULAR_EXPRESSION = "^([a-zA-Z0-9_-]+/)+$";

    public static String decoratePath(String path) {
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        path += "/";
        return path;
    }

    public static boolean validatePath(String path) {
        return Pattern.matches(REGULAR_EXPRESSION, path);
    }

    public static String getParentFolder(String originPath){
        String prefRegex="^https?:\\/\\/res.cloudinary.com\\/tri-tranvan\\/image\\/upload\\/([a-zA-Z0-9])+\\/";
        String suffRegex="\\/image_([0-9]+).([a-z]+)$";
        originPath=originPath.replaceFirst(prefRegex, "");
        originPath=originPath.replaceAll(suffRegex, "");
        return originPath;
    }

//    public static void main(String[] args) {
//        String originPath="https://res.cloudinary.com/tri-tranvan/image/upload/v1654848076/working-space/images/test/6/6/1654848074855/image_0.jpg";
////        String path="working-space/images/test/";
//        String path=PATH.replaceAll("/","\\\\/");
//        String prefRegex="^https:\\/\\/res.cloudinary.com\\/tri-tranvan\\/image\\/upload\\/([a-zA-Z0-9])+\\/"+path+"([0-9])+\\/([0-9])\\/";
//        String suffRegex="\\/image_([0-9]+).([a-z]+)$";
//        originPath=originPath.replaceFirst(prefRegex, "");
////        System.out.println("originPath : "+originPath);
//        originPath=originPath.replaceAll(suffRegex, "");
//        System.out.println("after format : "+originPath);
//
////        System.out.println("path : "+path);
////        String res=path.replaceFirst("^$")
//    }
}
