package com.example.ali.s.samplepdf;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnCreate;
    EditText editText,editText2,edittextshomare,edittexttarihk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = (Button)findViewById(R.id.create);
        editText =(EditText) findViewById(R.id.edittext1);
        editText2 =(EditText) findViewById(R.id.edittext2);
        edittextshomare =(EditText) findViewById(R.id.edittextshomare);
        edittexttarihk =(EditText) findViewById(R.id.edittexttarihk);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                createPdf(editText.getText().toString(),editText2.getText().toString(),edittextshomare.getText().toString(),edittexttarihk.getText().toString());
            }
        });
    }

    /*public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        v.draw(c);

        return bitmap;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(String title,String description,String edittextshomare,String edittexttarihk){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
       // float hight =7440 ;
        //float width = 10500 ;
        double width = 2480 / 25.4 * 72.0;
        double hight = 3508 / 25.4 * 72.0;

        int convertHighet = (int) hight, convertWidth = (int) width;



        //table.setTotalWidth(width);
       // table.setLockedWidth(true);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);


        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.kame);

        Matrix matrix = new Matrix();
        matrix.setRotate(0,bitmap.getWidth() / 2,  bitmap.getHeight() / 2 // py
        );
        matrix.postTranslate(
                canvas.getWidth() / 2 - bitmap.getWidth() / 2,
                canvas.getHeight() / 2 - bitmap.getHeight() / 2
        );


        //bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
        //canvas.drawBitmap(bitmap,0,0,paint);
        canvas.drawBitmap(bitmap, matrix, paint);



        canvas.drawText(edittextshomare, 1200, 800, paint);
        canvas.drawText(edittexttarihk, 1706, 1000, paint);


        canvas.drawText(title, 6050, 2150, paint);
        canvas.drawText(description, 5900, 2400, paint);

        // canvas.drawText(description,1,20,20.0f,30.0f,paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);


        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+title+".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
}