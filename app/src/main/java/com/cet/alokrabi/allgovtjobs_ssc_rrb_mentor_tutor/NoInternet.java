package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NoInternet extends AppCompatActivity {
    ImageView imageView;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoInternet.this , MainActivity.class);
                startActivity(intent);
            }
        });
        imageView = (ImageView)findViewById(R.id.imageView);
    }

}

