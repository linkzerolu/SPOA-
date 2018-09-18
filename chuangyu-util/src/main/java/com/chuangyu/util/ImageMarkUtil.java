package com.chuangyu.util;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author 赖维龙
 * @date 2018年08月01日 15:03
 */
public class ImageMarkUtil {

    /**
     * 校验图片的大小
     * @param file 文件
     * @return true：上传图片小于图片的最大值
     */
    /*public static boolean checkImageSize(File file) {
        if (!file.exists()) {
            return false;
        }
        // 图片大小
        Long size = file.length() / 1024;
        // 图片最大不能超过2M
        Long maxImageSize = 2 * 1024L;

        if (size > maxImageSize) {
            return true;
        }
        return false;
    }*/

    /**
     * 把图片印刷到图片上
     * @param imgIcon --水印文件
     * @param srcImgPath --目标文件
     * @param targerPath --保存位置
     * @param x --x坐标
     * @param y --y坐标
     */
    public static boolean markImageByIcon(ImageIcon imgIcon, String srcImgPath,
                                          String targerPath, Integer degree,int x,int y,String suffix) {
        OutputStream os = null;
        OutputStream eightPath = null;
        OutputStream fiftyPath = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            //水印剪切

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            /**
             * 得到画笔对象
             * Graphics g= buffImg.getGraphics()
             */
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                                .getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            //ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();
            // 透明度
            float alpha = 0.1f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 表示水印图片的位置
            g.drawImage(img, x, y, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targerPath+"-sy"+"."+suffix);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

            /**
             * 图片裁剪
             * 生成800*800
             */
            BufferedImage eight = ImageMarkUtil.resizeImage(buffImg, 800) ;
            eightPath = new FileOutputStream(targerPath+"-800+800"+"."+suffix);
            ImageIO.write(eight, "JPG", eightPath);

            /**
             * 图片裁剪
             * 生成50*50
             */
            BufferedImage fifty = ImageMarkUtil.resizeImage(buffImg, 100) ;
            fiftyPath = new FileOutputStream(targerPath+"-100+100"+"."+suffix);
            ImageIO.write(fifty, "JPG", fiftyPath);

            //System.out.println("图片完成添加Icon。。。。。。")
            return true ;
        } catch (Exception e) {
            e.printStackTrace();
            return false ;
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != eightPath) {
                    eightPath.close();
                }
                if (null != fiftyPath) {
                    fiftyPath.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片裁剪
     * @param im 原始图像
     * @param maxLength 需要裁剪的最长宽或者高
     * @return 返回处理后的图像
     */
    public static BufferedImage resizeImage(BufferedImage im, int maxLength) {
        /* 原始图像的宽度和高度 */
        float width = im.getWidth();
        float height = im.getHeight();
        int toWidth;
        int toHeight;
        if (width > height) {
            toWidth = maxLength;
            toHeight = (int) ((height / width) * toWidth);
        } else {
            toHeight = maxLength;
            toWidth = (int) (width / height * toHeight);
        }

        /* 新生成结果图片 */
        BufferedImage result = new BufferedImage(toWidth, toHeight,BufferedImage.TYPE_INT_RGB);

        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight,java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }

    /**
     * IMOperation operation = new IMOperation();
     * 		operation.addImage("D:\\file\\watermark\\watermark-1.png");
     * 		operation.rotate(-30d);
     * 		operation.addImage("D:\\file\\watermark\\watermark.png");
     *
     * 		ConvertCmd cmd = new ConvertCmd();
     * 		//Windows需要设置，Linux不需要
     * 		cmd.setSearchPath("D:\\Program Files\\ImageMagick-7.0.8-Q16");
     * 		cmd.run(operation);
     */
    public static void main(String[] args) throws Exception{
        /**文字水印测试*/
        //原图片
        //String filePath = "D:\\file\\ceshi\\fj.jpg"
        //生成之后的图片
        //String newFilePath = "D:\\file\\ceshi\\fj_water.jpg"
        //ImageMarkUtil.createMark(filePath,newFilePath)

        /**图片水印测试*/
        //原图片
        //String srcImgPath = "D:\\file\\ceshi\\fj.jpg"
        //水印图片
        //String iconPath = "D:\\file\\watermark\\watermark.png"
        //生成之后的图片
        //String targerPath = "D:\\file\\ceshi\\markImageByIcon.jpg"
        // 给图片添加水印
        //ImageMarkUtil.markImageByIcon(iconPath, srcImgPath, targerPath, null, 10, 10);

        /**图片压缩测试*/
        /**
        String srcImagePath = "D:\\photo\\caf51f3678201a20b059b50a8a5e73ba-2.jpg" ;
        String destImagePath = "D:\\photo\\caf51f3678201a20b059b50a8a5e73ba-2.jpg" ;
        BufferedImage bimg = ImageIO.read(new File(srcImagePath));

        IMOperation operation = new IMOperation();
        operation.addImage(srcImagePath);
        Integer width = bimg.getWidth()*19/20 ;
        Integer height = bimg.getHeight()*19/20 ;
        operation.resize(width,height);
        operation.addImage(destImagePath);

        ConvertCmd cmd = new ConvertCmd();
        //Windows需要设置，Linux不需要
        cmd.setSearchPath("D:\\Program Files\\ImageMagick-7.0.8-Q16");
        cmd.run(operation);
        */
    }
}
