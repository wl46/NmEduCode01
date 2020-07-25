package com.ningmeng.media;
import com.ningmeng.framework.utils.Mp4VideoUtil;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestMedia
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public class TestMedia {

    @Test
    public void testProcessBuilder(){
        //创建ProcessBuilder对象
        ProcessBuilder processBuilder = new ProcessBuilder();
        //设置第三方应用的命令
        processBuilder.command("pinh","127.0.0.1");
        processBuilder.command("ipconfig");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //存入输入流
            InputStream inputStream = start.getInputStream();
            //将这个输入流转为字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len=-1;
            char[] c = new char[1024];
            StringBuffer outBuffer = new StringBuffer();
            while ((len=inputStreamReader.read(c))!=-1){
                String s = new String(c, 0, len);
                outBuffer.append(c);
                System.out.print(s);
            }
            //获取进程
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFFmpeg(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> command = new ArrayList<>();
        command.add("E:/TwoProjectPlug-ins/ffmpeg-20180227-fa0c9d6-win64-static/bin/ffmpeg.exe");
        command.add("-i");
        command.add("E:/Ffmpeg_text/haicaowu.avi");
        command.add("‐y");//覆盖输出文件
        command.add("‐c:v");
        command.add("libx264");
        command.add("‐s");
        command.add("1280x720");
        command.add("‐pix_fmt");
        command.add("yuv420p");
        command.add("‐b:a");
        command.add("63k");
        command.add("‐b:v");
        command.add("753k");
        command.add("‐r");
        command.add("18");
        command.add("E:/Ffmpeg_text/haicaowu.mp4");
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            InputStreamReader comm = new InputStreamReader(inputStream, "gbk");
            int len=-1;
            char[] chars = new char[1024];
            while ((len=comm.read(chars))!=-1){
                String s = new String(chars, 0, len);
                System.out.println(s);
            }
          inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testM5p4VideoUtil(){
        String luj="E:/TwoProjectPlug-ins/ffmpeg-20180227-fa0c9d6-win64-static/bin/ffmpeg.exe";
        String ss="E:/Ffmpeg_text/haicaowu.avi";
        String dd="/haicaowu.mp4";
        String dbn="E:/Ffmpeg_text";
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(luj, ss, dd, dbn);
        String s = mp4VideoUtil.generateMp4();
        System.out.println(s);

    }
}
