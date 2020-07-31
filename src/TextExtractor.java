import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
public class TextExtractor {
    String  imgfileaddres ;
    TextExtractor(String imgfileaddres){

        this.imgfileaddres=(imgfileaddres);
    }
    public void Extract(String KeyAddress,String hidefolder_adress) throws IOException{
           BufferedImage Key=ImageIO.read(new File(KeyAddress));
           BufferedImage Hiddenimg=ImageIO.read(new File(imgfileaddres));
           ArrayList<Character> Charbin_sp=new ArrayList<Character>();
           for(int i=0;i<Key.getWidth();i++){
               for(int j=0;j<Key.getHeight();j++){
                   int rgb=Key.getRGB(i,j);
                   Color c=new Color(rgb);
                   if (c.getBlue()==255){
                       int rgb2=Hiddenimg.getRGB(i,j);
                       int blue = 255 & (rgb2);
                       Charbin_sp.add((char) (blue & 1));
                   }
                   if (c.getGreen()==255){
                       int rgb2=Hiddenimg.getRGB(i,j);
                       int green = 255 & (rgb >> 8);
                       Charbin_sp.add((char) (green & 1));

                   }
                   if (c.getRed()==255){
                       int rgb2=Hiddenimg.getRGB(i,j);
                       int red = 255 & (rgb >> 16);
                       Charbin_sp.add((char) (red & 1));
                   }

               }
           }
           ArrayList<String> Charbin=new ArrayList<String>();
           for (int i=0;i<Charbin_sp.size()/8;i++){
               for(int j=0;j<8;j++){
                   Charbin.add(i,String.valueOf(Charbin_sp.get(i+j)));
               }
           }
           String words="";
           for (int i=0; i< Charbin.size();i++){
                                                    //finding askii decimal -> charint
               int charint=0;
               for(int j=Charbin.get(i).length()-1,k=0;j>=0;j--,k++){
                   if(Charbin.get(i).charAt(j)=='1'){
                       charint += Math.pow(2,k);
                   }
               }
               char wordcharacter = (char)(charint);
               words += wordcharacter;
           }

        PrintWriter out = new PrintWriter(hidefolder_adress+"OncodedText.txt");
        out.print(words);
        out.close();


    }
}

