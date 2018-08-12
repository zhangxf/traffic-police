package org.trafficpolice.controller;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class CaptchaUtil {

    private static Random random = new Random();
    private BufferedImage image;// 图像  
    private String str;// 验证码  
    private static char code[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();  
  
    public static final String SESSION_CODE_NAME="code";  
      
    private CaptchaUtil() {  
        init();// 初始化属性  
    }  
  
    /* 
     * 取得RandomNumUtil实例 
     */  
    public static CaptchaUtil Instance() {  
        return new CaptchaUtil();  
    }  
  
    /* 
     * 取得验证码图片 
     */  
    public BufferedImage getImage() {  
        return this.image;  
    }  
  
    /* 
     * 取得图片的验证码 
     */  
    public String getString() {  
        return this.str;  
    }  
  
    private void init() {  
        // 在内存中创建图象  
        int width = 85, height = 20;  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        // 获取图形上下文  
        Graphics g = image.getGraphics();  
        // 生成随机类  
        Random random = new Random();  
        // 设定背景色  
        g.setColor(getRandColor(200, 250));  
        g.fillRect(0, 0, width, height);  
        // 设定字体  
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));  
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到  
        g.setColor(getRandColor(160, 200));  
        for (int i = 0; i < 155; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int xl = random.nextInt(12);  
            int yl = random.nextInt(12);  
            g.drawLine(x, y, x + xl, y + yl);  
        }  
        // 取随机产生的认证码(4位数字)  
        String sRand = "";  
        for (int i = 0; i < 4; i++) {  
            String rand = String.valueOf(code[random.nextInt(code.length)]);  
            sRand += rand;  
            // 将认证码显示到图象中  
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));  
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成  
            g.drawString(rand, 13 * i + 6, 16);  
        }  
        // 赋值验证码  
        this.str = sRand;  
  
        // 图象生效  
        g.dispose();  
        // ByteArrayInputStream input = null;  
        // ByteArrayOutputStream output = new ByteArrayOutputStream();  
        // try {  
        // ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);  
        // ImageIO.write(image, "JPEG", imageOut);  
        // imageOut.close();  
        // input = new ByteArrayInputStream(output.toByteArray());  
        // } catch (Exception e) {  
        // System.out.println("验证码图片产生出现错误：" + e.toString());  
        // }  
        // this.image = input  
        this.image = image;/* 赋值图像 */  
    }  
  
    /* 
     * 给定范围获得随机颜色 
     */  
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();  
        if (fc > 255)  
            fc = 255;  
        if (bc > 255)  
            bc = 255;  
        int r = fc + random.nextInt(bc - fc);  
        int g = fc + random.nextInt(bc - fc);  
        int b = fc + random.nextInt(bc - fc);  
        return new Color(r, g, b);  
    }


    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }
    }
    /**
     * Base64编码的验证码图片
     * @param w
     * @param h
     * @param code
     * @return
     * @throws Exception
     */
    public static String imageToBase64(int w, int h, String code) throws Exception {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Arial", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                    (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }
        g2.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return new BASE64Encoder().encode(baos.toByteArray());
    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     *
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void base64ToImage(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            File file = new File("D:/test.jpg");
            FileOutputStream write = new FileOutputStream(file);
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成指定长度的随机数字和字母
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            switch (charOrNum) {
                case "char":
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char) (random.nextInt(26) + temp);
                    break;
                case "num":
                    val += String.valueOf(random.nextInt(10));
                    break;
            }
        }
        return val;
    }

    public static void main(String[] args)throws Exception {
        System.out.println(CaptchaUtil.Instance().getImage());
        System.out.println(CaptchaUtil.Instance().getString());
        base64ToImage("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");
     //   base64ToImage("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");
       // base64ToImage("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiMmU2YWU0OGZhYjY0OWM2YjljMWM4MDFiN2MwN2Q0NSIsImF1ZCI6InBjIiwiZXhwIjoxNTA4MTU1NDQxLCJpYXQiOjE1MDc1NTA2NDF9.EqYNOBFtjU2eYgYGfctcR5MjlWu1ud4WNLB1qMiAGr6G4-hw9pDyUmFGg2IXTcKcsEUdZn-hUqqgCtG2RryiJA");
      //  base64ToImage("/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\r\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\r\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAoAHgDASIA\r\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\r\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\r\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\r\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\r\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\r\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\r\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\r\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1Z9St\r\nQ7RRyiaZTgxQne4PuB054ycAdyKjLX9xIBs+yQHjcCry9+o5VRwOm/O7+HGasmSWNiDEXXOQUP8A\r\nMGuM1bxTM05XTQRDCdj3nlh3AY8hc8Y479cDmtpTUdzWTsddDbRWk+7zpP3nyIsk7tk8k43MQTgd\r\nh2NWWfYGJVtqjOQM5+gHOa41INXht1v9L13+0E2lmilzkj0Cknng+h4re0bVIdYghu/KAm2MhxGT\r\nsIK7hv6AH5DjvjvjhQlfQIvW1i9NPLFLjylMbbQrAsTnktkBSAAoGDnknHHGeVhuNd8RvLJYzfYb\r\nFX2gkncxzk89c+3A7V2DBznYyjg9Vzz271TuNUsIJJFlvo4mtiDKhYA/MpIBB5weox1K+xFOUb7s\r\n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\r\nj+J3AdeOT054OSiik9NB7n//2Q==");
    }
}