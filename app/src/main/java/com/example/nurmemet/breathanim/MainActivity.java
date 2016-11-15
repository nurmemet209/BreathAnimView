package com.example.nurmemet.breathanim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BreathAnimView mBreathView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBreathView= (BreathAnimView) findViewById(R.id.surface_view);
    }

    public void toSecondAct(View view){
        Intent it=new Intent(this,SecondAct.class);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        mBreathView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBreathView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBreathView.onDestroy();
        super.onDestroy();
    }
}
