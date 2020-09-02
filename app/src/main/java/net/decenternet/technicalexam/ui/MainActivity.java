package net.decenternet.technicalexam.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.ui.tasks.TasksActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBrandingLogo;
    public static Boolean theCatalyst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBrandingLogo = (ImageView) findViewById(R.id.ivBrandingLogo);

        Glide.with(this)
                .load(R.drawable.image_qoute)
                .into(ivBrandingLogo);

        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(
                        MainActivity.this,
                        TasksActivity.class
                );
                startActivity(mainIntent);
                finish();
                theCatalyst = false;
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}