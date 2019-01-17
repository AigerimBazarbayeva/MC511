//package fh.ooe.mcm.inactivitytracker.charts;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//import fh.ooe.mcm.inactivitytracker.R;
//
//public class HomeActivity extends AppCompatActivity {
//    Button calculateButton;
//    Button chartingButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        calculateButton = (Button) findViewById(R.id.calculateButton);
//        calculateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, MainActivity.class));
//            }
//        });
//
//        chartingButton = (Button) findViewById(R.id.chartingButton);
//        chartingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, Main2Activity.class));
//            }
//        });
//    }
//}
