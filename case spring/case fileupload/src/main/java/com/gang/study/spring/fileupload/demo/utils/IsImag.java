package com.gang.study.spring.fileupload.demo.utils;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;

@SuppressWarnings("ConstantConditions")
public class IsImag {
    public static boolean isImage(File tempFile)
            throws Exception {
        ImageInputStream is= ImageIO.createImageInputStream(tempFile);
        return is!=null;
    }
}
