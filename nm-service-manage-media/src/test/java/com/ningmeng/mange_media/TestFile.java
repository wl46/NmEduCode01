package com.ningmeng.mange_media;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: TestFile
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public class TestFile {
    //测试文件分块
   @Test
   public void TestChunk() throws IOException {

       File sourseFile = new File("E:\\Ffmpeg_text\\haicaowu.avi");
          //分块的路径
       String chunkFileFolder="E:\\Ffmpeg_text\\chunks\\";
        //计算文件大小
       long ChunkFilesize=1*1024*1024;
       long chunknum= (long) (sourseFile.length()*1.0/ChunkFilesize);
          //创建读文件 的对象
       RandomAccessFile raf_read = new RandomAccessFile(sourseFile, "r");
          //缓存区
       byte[] b = new byte[1024];
       //分块
       for(int i=0;i<chunknum;i++){
           //创建分块文件
           File file = new File(chunkFileFolder+i);
           boolean newFile = file.createNewFile();
           if(newFile){
            //向分块文件中写数据
               RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
               int len = -1;
               while((len = raf_read.read(b))!=-1){
                   raf_write.write(b,0,len);
                   if(file.length()>ChunkFilesize){
                       break;
                   }
               }
               raf_write.close();
           }
       }
              raf_read.close();
   }
   //测试文件合并
    @Test
    public void testMergeFile() throws IOException {
       //块文件目录
        String chunkFileFolderPhat="E:\\Ffmpeg_text\\chunks\\";
        //块文件对象
        File chunkFileFolder = new File(chunkFileFolderPhat);
       //块文件列表
        File[] files = chunkFileFolder.listFiles();
        //将块文件排序，按升序
        List<File> filesList = Arrays.asList(files);
        Collections.sort(filesList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if(Integer.parseInt(o1.getName())>Integer.parseInt(o2.getName())){
                    return 1;
                }
                return -1;
            }
        });
        //合并文件名
        File sourseFile = new File("E:\\Ffmpeg_text\\haicaowu_mach.avi");
        //创建新的文件
        boolean newFile = sourseFile.createNewFile();
        //创建写对象
        RandomAccessFile raf_write = new RandomAccessFile(sourseFile, "rw");

        byte[] b = new byte[1024];

        for (File chunkFile : filesList) {
            //创建一个读取块文件
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
            int len=-1;
            while ((len=raf_read.read(b))!=-1){
                raf_read.write(b,0,len);
            }
            raf_read.close();
        }
        raf_write.close();

    }
}
