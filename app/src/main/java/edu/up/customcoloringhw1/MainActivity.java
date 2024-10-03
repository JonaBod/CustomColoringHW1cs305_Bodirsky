package edu.up.customcoloringhw1;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <!-- class MainActivity -->
 *
 * @author Jona Bodirsky
 * @version 09/30/24
 *
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Reference the TextView, seekbars and PaintingView
        TextView elementTextView = findViewById(R.id.tvElement);
        SeekBar seekRed = findViewById(R.id.seekRed);
        SeekBar seekGreen = findViewById(R.id.seekGreen);
        SeekBar seekBlue = findViewById(R.id.seekBlue);

        PaintingView paintingView = findViewById(R.id.paintingView);

        // Create and set the controller
        PaintingController controller = new PaintingController(paintingView, elementTextView, seekRed, seekGreen, seekBlue);
        paintingView.setController(controller);

        // set listeners for seekbar changes
        seekRed.setOnSeekBarChangeListener(controller);
        seekGreen.setOnSeekBarChangeListener(controller);
        seekBlue.setOnSeekBarChangeListener(controller);
    }

}