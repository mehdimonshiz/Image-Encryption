import java.io.IOException;
import java.io.FileNotFoundException;
public class main {
    public static void main(String[] args) throws IOException ,FileNotFoundException {
        TextHider hider =new TextHider("text.txt");

        hider.hide("\\C:\\Users\\Mr. Mehdi\\IdeaProjects\\FINALPRJ\\target.bmp","\\C:\\Users\\Mr. Mehdi\\IdeaProjects\\FINALPRJ\\hide folder");

    }

}
