package it.diamonds;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.lwjgl.Sys;


public final class BugReport
{
    private static final String MSG_BOX_TITLE = "Diamond Crush";

    private static final String MSG_BOX_MESSAGE = "A crash has been detected, execution terminated."
        + "\n\nPlease send bug-report.txt via email"
        + " to support@diamondcrush.net";


    private BugReport()
    {
    }


    public static void showDramaticMessageBox()
    {
        Sys.alert(MSG_BOX_TITLE, MSG_BOX_MESSAGE);
    }


    public static void writeBugReport(Exception ex)
    {
        try
        {
            PrintStream ps = new PrintStream("bug-report.txt");
            OutputStreamWriter out = new OutputStreamWriter(ps);

            out.write("OS: " + System.getProperty("os.name") + "\n");
            out.write("Version: " + System.getProperty("os.version") + "\n");
            out.write("Architecture: " + System.getProperty("os.arch") + "\n\n");

            out.write("VM Vendor: " + System.getProperty("java.vendor") + "\n");
            out.write("Version: " + System.getProperty("java.version") + "\n\n");

            out.write("Class Path: " + System.getProperty("java.class.path")
                + "\n");

            out.write("JNI Library Path: "
                + System.getProperty("java.library.path") + "\n\n");

            out.write("Exception: " + ex.getClass().toString() + "\n");
            out.write("Message: " + ex.getMessage() + "\n");

            // TODO: perche' su linux ritorna sempre null null ?
            out.write("Display Adapter Driver: "
                + org.lwjgl.opengl.Display.getAdapter() + " "
                + org.lwjgl.opengl.Display.getVersion() + "\n\n");

            out.write("Stacktrace:\n" + stackToString(ex) + "\n");
            out.close();
        }
        catch (FileNotFoundException e)
        {
            Sys.alert("Diamond Crush", "Can't open bug-report.txt");
        }
        catch (IOException e)
        {
            Sys.alert("Diamond Crush", "Can't write to bug-report.txt");
        }
    }


    private static String stackToString(Exception e)
    {
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
        catch (Exception e2)
        {
            return "bad stack2string";
        }
    }
}
