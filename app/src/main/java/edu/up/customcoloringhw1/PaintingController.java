package edu.up.customcoloringhw1;

import android.graphics.Color;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * <!-- class PaintingController -->
 *
 * @author Jona Bodirsky
 * @version 09/30/24
 *
 */

public class PaintingController implements SeekBar.OnSeekBarChangeListener{

    private PaintingView paintingView;
    private TextView tvElementName;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;

    /** ctor for the controller */
    public PaintingController(PaintingView view, TextView tv, SeekBar rSeek, SeekBar gSeek, SeekBar bSeek){
        this.paintingView = view;
        this.tvElementName = tv;
        this.redSeekBar = rSeek;
        this.greenSeekBar = gSeek;
        this.blueSeekBar = bSeek;

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // Update the paintingView with the new color
        if (fromUser) {
            int red = redSeekBar.getProgress();
            int green = greenSeekBar.getProgress();
            int blue = blueSeekBar.getProgress();

            // Change the color of the last tapped element
            paintingView.updateElementColor(red, green, blue);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


    // Update TextView with the currentElement name
    public void updateElementTV(String elementName){
        tvElementName.setText(elementName);
    }

    // Update seekbars to show color values of currentElement
    public void updateSeekBars(int color) {
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

    }

}
