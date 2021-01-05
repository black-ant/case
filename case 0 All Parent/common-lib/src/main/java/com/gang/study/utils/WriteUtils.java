package com.gang.study.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @Classname WriteUtils
 * @Description TODO
 * @Date 2019/11/6 21:11
 * @Created by ant-black 1016930479@qq.com
 */
public class WriteUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * write with path , suffix and content
     *
     * @param path
     * @param context
     * @param suffix
     */
    public void writeFile(String path, String context, String suffix) {

        String filePath = new StringBuilder().append(path).append(".").append(suffix).toString();
        File file = FileUtils.isExistDirAndBack(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            fos.write(context.getBytes(Charset.forName("UTF-8")));
            // 换行
            fos.write(System.getProperty("line.separator").getBytes());

            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }


    /**
     * IO InputStream write file
     *
     * @param uploadedInputStream
     * @param uploadedFileLocation
     */
    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[4096];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("upload-file save entity local fail: ", e);
        }

    }

    //图片缩略图,取出图片并且压缩
    private void writeToimg(String uploadedFileLocationnew, String uploadedFileLocation, String type) {
        scaleImageWithParams(uploadedFileLocation, uploadedFileLocationnew, 200, 240, true, type.substring(1));
    }


    /**
     * *
     * 将图片缩放到指定的高度或者宽度
     *
     * @param sourceImagePath 图片源地址
     * @param destinationPath 压缩完图片的地址
     * @param width           缩放后的宽度
     * @param height          缩放后的高度
     * @param auto            是否自动保持图片的原高宽比例
     * @param format          图图片格式 例如 jpg
     */
    public static void scaleImageWithParams(String sourceImagePath,
                                            String destinationPath, int width, int height, boolean auto, String format) {

        try {
            File file = new File(sourceImagePath);
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file);
            if (auto) {
                ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage, width, height);
                width = paramsArrayList.get(0);
                height = paramsArrayList.get(1);
                System.out.println("自动调整比例，width=" + width + " height=" + height);
            }

            Image image = bufferedImage.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(outputImage, format, new File(destinationPath));
        } catch (Exception e) {
            System.out.println("scaleImageWithParams方法压缩图片时出错了");
            e.printStackTrace();
        }

    }

    /**
     * *
     *
     * @param bufferedImage 要缩放的图片对象
     * @param width_scale   要缩放到的宽度
     * @param height_scale  要缩放到的高度
     * @return 一个集合，第一个元素为宽度，第二个元素为高度
     */
    private static ArrayList<Integer> getAutoWidthAndHeight(BufferedImage bufferedImage, int width_scale, int height_scale) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        double scale_w = getDot2Decimal(width_scale, width);

        System.out.println("getAutoWidthAndHeight width=" + width + "scale_w=" + scale_w);
        double scale_h = getDot2Decimal(height_scale, height);
        if (scale_w < scale_h) {
            arrayList.add(parseDoubleToInt(scale_w * width));
            arrayList.add(parseDoubleToInt(scale_w * height));
        } else {
            arrayList.add(parseDoubleToInt(scale_h * width));
            arrayList.add(parseDoubleToInt(scale_h * height));
        }
        return arrayList;

    }

    /**
     * *
     * 返回两个数a/b的小数点后三位的表示
     *
     * @param a
     * @param b
     * @return
     */
    public static double getDot2Decimal(int a, int b) {

        BigDecimal bigDecimal_1 = new BigDecimal(a);
        BigDecimal bigDecimal_2 = new BigDecimal(b);
        BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2, new MathContext(4));
        Double double1 = new Double(bigDecimal_result.toString());
        System.out.println("相除后的double为：" + double1);
        return double1;
    }

    /**
     * 将double类型的数据转换为int，四舍五入原则
     *
     * @param sourceDouble
     * @return
     */
    private static int parseDoubleToInt(double sourceDouble) {
        int result = 0;
        result = (int) sourceDouble;
        return result;
    }
}
