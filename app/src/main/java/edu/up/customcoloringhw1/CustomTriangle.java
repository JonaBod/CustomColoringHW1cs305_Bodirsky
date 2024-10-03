package edu.up.customcoloringhw1;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * <!-- class CustomTriangle -->
 * This class defines a custom drawing element that is a rectangle.
 *
 * @author Jona Bodirsky
 * @version 09/30/24
 *
 */

/*
External Citation
Custom Element classes made available by Nuxoll
copied and adjusted for triangles
 */

public class CustomTriangle extends CustomElement{

    // variables necessary for the triangle
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;
    private int width;
    private int height;

    Path path = new Path();

    /** ctor for triangle */
    public CustomTriangle(String name, int color, int x, int y, int initW, int initH){
        super(name,color);

        this.x1 = x;
        this.y1 = y;
        this.width = initW;
        this.height = initH;

        // for a triangle having the bottom be horizontal
        this.x2 = x1 + width;
        this.y2 = y1;
        this.x3 = x1 + width/2;
        this.y3 = y1 - initH;

    }


    /* External sources:
    https://kylewbanks.com/blog/drawing-triangles-rhombuses-and-other-shapes-on-android-canvas
    explained how to draw custom objects using draw path
     */
    public void drawMe(Canvas canvas){

        int halfW = width/2;

        path.reset();
        path.moveTo(x1,y1);
        path.lineTo(x1+width,y1);
        path.lineTo(x1+halfW,y1-height);
        path.close();

        canvas.drawPath(path, myPaint);
        canvas.drawPath(path, outlinePaint);
    }

    /*
    External Citation
    Problem: Trying to figure out if a point is within a triangle
    https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/
    10/01/2024
    Used for containsPoint method
     */
    @Override
    public boolean containsPoint(int x, int y) {

        /* Calculate area of triangle ABC */
        int A = area (x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        int A1 = area (x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        int A2 = area (x1, y1, x, y, x3, y3);

        /* Calculate area of triangle PAB */
        int A3 = area (x1, y1, x2, y2, x, y);

        /* Check if sum of A1, A2 and A3 is close to A */
        return ((A1 + A2 + A3 - TAP_MARGIN) < A && A < (A1 + A2 + A3 + TAP_MARGIN));
        /*
         Required to add a margin because the int typecast of getting
         X/Y touch location makes the area calculation inaccurate sometimes
        */
    }


    /* A utility function to calculate area of triangle
       formed by (x1, y1) (x2, y2) and (x3, y3) */
    public int area(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2))/2);
    }

    @Override
    public int getSize() {
        return (width * height)/2;
    }

    @Override
    public void drawHighlight(Canvas canvas) {

        canvas.drawPath(path, highlightPaint);
        canvas.drawPath(path, outlinePaint);

    }

}
