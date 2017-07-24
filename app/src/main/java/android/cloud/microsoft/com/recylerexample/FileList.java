package android.cloud.microsoft.com.recylerexample;

import android.os.Environment;
import android.util.Log;
import java.io.File;

/**
 * Created by mohit on 20/7/17.
 */
public class FileList {

    String state = Environment.getExternalStorageState();
    private  String [][] data;
    String fileName;


    public FileList(String fileName)
    {
        this.fileName = fileName;
    }


    public String [][] getFileList()
    {

     if(Environment.MEDIA_MOUNTED.equals(state))

        {

        //This provides the path of Music Directory as given in my phone. emulated/0/music/


            final File musicFilePath;
            if(fileName.compareTo("root")==0)
            musicFilePath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC);
            else
            {
                musicFilePath = new File(fileName);
            }
            Log.i("CustomError",musicFilePath.toString());
            File [] fileList =musicFilePath.listFiles();
            if(fileList.length == 0)
            {
                Log.i("CusttomError","Null");
            }

           data  = new String[fileList.length][3];
            for(int i =0; i< fileList.length;i++)
            {
                String baseName[]=  fileList[i].toString().split("/");
                data[i][0] = baseName[baseName.length-1];

                if(fileList[i].isDirectory())
                {
                    data[i][1] = Integer.toString(fileList[i].listFiles().length)+" files";
                    data[i][2] = "folder";
                }
                else
                {
                    data[i][1] = Integer.toString((int)fileList[i].length()/1024)+" Kb";
                    int length = fileList[i].toString().length();
                    data[i][2] = fileList[i].toString().substring(length-3,length);
                }
            }

        }
     else
        {
            Log.i("CustomError", "SD card not inserted");
        }
        return data;
    }
}


