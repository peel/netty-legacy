import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class GlobusCli {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));

    private void run() throws Exception {
        channelActive();
        String startOfs  = reader.readLine();
        if(startOfs.equals("Y")){
            System.out.print("? ");
            while(!reader.readLine().equals("EXIT")){
                System.out.println("RESPONSE");
                System.out.print("? ");
            }
            do{
                System.out.print("AIX-/developers/ctd/bnk/bnk.run: ");
            }
            while(!reader.readLine().equals("EXIT"));
        }else if(startOfs.equals("N")){
            System.out.print("START GLOBUS Y/N=");
            if(reader.readLine().equals("Y")){
                System.out.print("Enter your terminal type or <return> for no change : ");
                reader.readLine();
                System.out.print("PLEASE ENTER YOUR SIGN ON NAME");
                while(!reader.readLine().equals("EXIT")){
                    System.out.print("jBASE debugger-> ");
                }
            }
            do{
                System.out.print("AIX-/developers/ctd/bnk/bnk.run: ");
            }while(!reader.readLine().equals("exit"));
        }
    }

    private static void channelActive() {
        System.out.print("START OFS Y/N=");
    }

    public static void main(String[] args) throws Exception {
        new GlobusCli().run();
    }

}
