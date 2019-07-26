package codechallenge.jbaires.xoom.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import codechallenge.jbaires.xoom.R;
import codechallenge.jbaires.xoom.utils.NetworkUtils;

public class CheckInternetAccess extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet_access);

        Button btnCheckInternet = (Button) findViewById(R.id.btnCheckInternet);

        btnCheckInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NetworkUtils.haveNetworkConnection(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "You still do not have internet access", Toast.LENGTH_SHORT).show();
                } else {
                    Intent mainActivityIntent = new Intent(CheckInternetAccess.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                }
            }
        });
    }
}
