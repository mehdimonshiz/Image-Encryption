import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.OutOfMemoryError;
import java.awt.Color;

public class TextHider  {
    private File readtext ;
    TextHider(String textfileaddres){

        readtext =new File(textfileaddres);
    }
    public void hide (String traget_img_address,String hidefolder_adress)throws IOException ,FileNotFoundException,IndexOutOfBoundsException{

        BufferedImage targetimg = ImageIO.read(new File(traget_img_address));
        BufferedImage raw=new BufferedImage(targetimg.getWidth(),targetimg.getHeight(),BufferedImage.TYPE_INT_RGB);
        BufferedImage FinalImage=new BufferedImage(targetimg.getWidth(),targetimg.getHeight(),BufferedImage.TYPE_INT_RGB);
        int pixelcount = targetimg.getWidth()*targetimg.getHeight();


        try {
            ArrayList<String> charbin=new ArrayList<String>();
            Scanner txt = new Scanner(readtext);
            while (txt.hasNextLine()){
                String L =txt.nextLine();
                for(int i =0;i<L.length();i++){
                    charbin.add(Integer.toBinaryString(L.charAt(i))) ;
                    while (charbin.get(i).length()<8) {
                        charbin.set(i,'0'+charbin.get(i));
                    }

                }


            }

            //characters of charbin parts

            ArrayList <Character> charbin_sp=new ArrayList<Character>();
            for(int i=0;i<charbin.size();i++) {
                for (int j = 0; j < 8; j++) {
                    charbin_sp.add(charbin.get(i).charAt(j));
                }
            }

            if (charbin_sp.size()>(0.75*pixelcount)){
                throw new IllegalArgumentException(" ERROR: your text is too long to hide!");
            }

            //random number of pixel

            ArrayList<Integer> rand_number =new ArrayList<Integer>();
            Random rand = new Random();

            for (int i=0;i<charbin_sp.size();i++){
                rand_number.set(i,rand.nextInt(pixelcount));
            }
            rand_number.sort(null);

            //making key and hide text into image (asl dastan)

            for (int i=0;i<rand_number.size();i++){
                int x = (rand_number.get(i))/targetimg.getWidth();
                int y = (rand_number.get(i))%targetimg.getWidth();
                int rgb = targetimg.getRGB(x,y);
                int blue = 255 & (rgb);
                int green = 255 & (rgb >> 8);
                int red = 255 & (rgb >> 16);

                int rand_color = rand.nextInt(3);
                if(rand_color == 0){    //red
                    red = (red&254)|charbin_sp.get(i);
                    Color rec = new Color(255,0,0);
                    raw.setRGB(x,y,rec.getRGB());
                }
                if(rand_color == 1){    //green
                    green = (green&254)|charbin_sp.get(i);
                    Color rec = new Color(0,255,0);
                    raw.setRGB(x,y,rec.getRGB());
                }
                if(rand_color == 2){    //blue
                    blue = (blue&254)|charbin_sp.get(i);
                    Color rec = new Color(0,0,255);
                    raw.setRGB(x,y,rec.getRGB());
                }
                Color hidden = new Color(red,green,blue);

                targetimg.setRGB(x,y,hidden.getRGB());

            }

            ImageIO.write(targetimg,"BMP", new File(hidefolder_adress+ "\\FinalImage.BMP"));
            ImageIO.write(raw,"BMP", new File(hidefolder_adress+ "\\key.BMP"));
            txt.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(" ERROR: there's no such a text");
            e.printStackTrace();
            }
        }


}
