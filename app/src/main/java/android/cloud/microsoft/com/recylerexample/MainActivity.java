package android.cloud.microsoft.com.recylerexample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    private ArrayList<FilePOJO> listContentArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        //As explained in the tutorial, LineatLayoutManager tells the RecyclerView that the view
        //must be arranged in linear fashion
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CustomAdapter(this,this);
        //Method call for populating the view
        populateRecyclerViewValues("root");

    }

    public void populateRecyclerViewValues(String fileName) {
        /** This is where we pass the data to the adpater using POJO class.
         *  The for loop here is optional. I've just populated same data for 50 times.
         *  You can use a JSON object request to gather the required values and populate in the
         *  RecyclerView.
         * */

        listContentArr = new ArrayList<>();
        FileList l = new FileList(fileName);
        String [][] data = l.getFileList();



        for(int i = 0 ;i< data.length;i++) {
            //Creating POJO class object
            FilePOJO pojoObject = new FilePOJO();
            //Values are binded using set method of the POJO class
            pojoObject.setFileName(data[i][0]);
            pojoObject.setDetail(data[i][1]);
            pojoObject.setFileImage(data[i][2]);
            //After setting the values, we add all the Objects to the array
            //Hence, listConentArr is a collection of Array of POJO objects
            listContentArr.add(pojoObject);
        }
        //We set the array to the adapter
        adapter.setListContent(listContentArr);
        //We in turn set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if(adapter.goBack())
        moveTaskToBack(true);
    }

    public void playFile(String clickedFile,String type)
    {
        File file = new File(clickedFile);
        Intent target = new Intent(Intent.ACTION_VIEW);
        if(type.compareTo("pdf")==0)
            target.setDataAndType(Uri.fromFile(file),"application/pdf");
        else if(type.compareTo("mp3")==0)
            target.setDataAndType(Uri.fromFile(file),"audio/*");
        else if(type.compareTo("txt")==0)
            target.setDataAndType(Uri.fromFile(file),"text/plain");
        else
            target.setDataAndType(Uri.fromFile(file),"image/*");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}
