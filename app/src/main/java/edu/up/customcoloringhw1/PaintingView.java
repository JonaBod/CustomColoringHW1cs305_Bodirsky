package edu.up.customcoloringhw1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * <!-- class PaintingView -->
 *
 * @author Jona Bodirsky
 * @version 09/30/24
 *
 */

public class PaintingView extends SurfaceView {


    private final ArrayList<CustomElement> elements = new ArrayList<>();
    private PaintingController paintingController;
    private CustomElement currentElement;

    // first initializing of colors
    int sky = Color.rgb(63,225,255);
    int grass = Color.rgb(107,242,107);
    int mountain = Color.rgb(146,146,146);
    int sun = Color.rgb(236,250,43);
    int houseBody = Color.rgb(255,255,255);
    int houseRoof = Color.rgb(190,43,43);
    int canvasWidth;
    int canvasHeight;



    /* external Citation
    Birthday cake Lab example
     */
    /** ctor for PaintingView calls the init method which initializes all the elements*/
    public PaintingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        init();

    }

    // Allows other parts of the program to access the paintingController
    public void setController(PaintingController controller) {
        this.paintingController = controller;
    }


    // ELEMENT INITIALIZATION
    private void init(){

        int width = canvasWidth;
        int height = canvasHeight;

        // additional specification for element size
        int mountW = width/2;
        int mountH = height*2/3;
        int grassLayer = height*7/8;

        // light blue rectangle background representing the sky
        CustomElement sky1 = new CustomRect("Sky", sky,0,0,width,height );
        elements.add(sky1);

        // grass around the bottom
        CustomElement grass1 = new CustomRect("Grass", grass, 0, grassLayer,width, height);
        elements.add(grass1);

        // grey triangle in the left representing a mountain

        CustomElement mountain1 = new CustomTriangle("Mountain", mountain, width/8, grassLayer, mountW, mountH);
        elements.add(mountain1);

        // yellow circle top left for the sun
        int sunR = height/10;
        CustomElement sun1 = new CustomCircle("Sun", sun, sunR*2, sunR*2, sunR);
        elements.add(sun1);

        // house on the right
        // main foundation white
        int houseBodyHeight = height/2;
        CustomElement houseBody1 = new CustomRect("HouseBody", houseBody, width*3/5, houseBodyHeight, width*3/5+width/4, grassLayer );
        elements.add(houseBody1);

        // red triangle roof
        CustomElement houseRoof1 = new CustomTriangle("HouseRoof", houseRoof, width*3/5, houseBodyHeight, width/4, height/5);
        elements.add(houseRoof1);

    }//init


    /*
    External Citation
    Problem: needed to access the canvas dimensions outside of the onDraw method
    https://stackoverflow.com/questions/6652400/how-can-i-get-the-canvas-size-of-a-custom-view-outside-of-the-ondraw-method
    09/30/2024
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.canvasWidth = w;
        this.canvasHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
        // Initialize elements with actual width and height
        init();
    }

    @Override
    public void onDraw(Canvas canvas){

        canvas.drawColor(Color.WHITE);

        /*
        External Citation
        Nuxoll
        Problem: have an efficient way to iterate through all the elements
        copied from in class activity
         */
        for(CustomElement currentElement : elements){
            currentElement.drawMe(canvas);
        }
        if (currentElement != null)  currentElement.drawHighlight(canvas);

    }//onDraw



    // onTouchEvent for simpler set up
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX();
            int y = (int)event.getY();

            for (int i = elements.size()-1; i>=0; i--) {
                CustomElement element = elements.get(i);
                if (element != null && element.containsPoint(x, y)) {
                    Log.d("element", "Touched element: " + element.getName()+" | touch loc x: "+x+" y: "+y);

                    currentElement = element; // Store the last touched element
                    // Invalidate so that the highlight will be drawn
                    invalidate();

                    // Update the TextView and SeekBars via the controller
                    if (paintingController != null) {
                        paintingController.updateElementTV(element.getName());
                        paintingController.updateSeekBars(element.getColor()); // Update SeekBars
                    } else {
                        Log.e("PaintingView", "PaintingController is null");
                    }
                    return true; // Return true if an element was touched
                }
            }
            Log.d("Touch", "No element was touched");
        }
        return false; // Return false if nothing was touched
    }

    // Updates color of the currentElement
    public void updateElementColor(int red, int green, int blue) {
        Log.d("color", "update method called");
        if (currentElement != null) {
            currentElement.setColor(Color.rgb(red, green, blue)); // Change color
            // call the onDraw method
            invalidate();
        }
    }
}
