package com.example.domis.assignment2.controller;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;
    private Context context;

    /* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        Log.e("MotionEvent", motionEvent.toString());
        return gestureDetector.onTouchEvent(motionEvent);
    }

    /**
     * Gets the gesture detector.
     *
     * @return the gesture detector
     */
    public GestureDetector getGestureDetector(){
        return  gestureDetector;
    }

    /**
     * Instantiates a new on swipe touch listener.
     *
     * @param context
     *            the context
     */
    public OnSwipeTouchListener(Context context) {
        super();
        this.context = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        private static final int  SWIPE_DISTANCE_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }
    }

    /**
     * On swipe right.
     */
    public void onSwipeRight() {
        Log.e("Swipe:", "right");
    }

    /**
     * On swipe left.
     */
    public void onSwipeLeft() {
        Log.e("Swipe:", "left");
    }

}
