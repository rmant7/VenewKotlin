package com.alex.sid.shante.locationbasedsocialnetwork.presentation.screens.map;

import android.util.Log;
import android.widget.TextView;

import com.alex.sid.shante.locationbasedsocialnetwork.R;

import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

public class MyMarkerInfoWindow extends BasicInfoWindow {

    protected Marker mMarkerRef; //reference to the Marker on which it is opened. Null if none.

    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     *                       bubble_subdescription, bubble_image
     * @param mapView
     */
    public MyMarkerInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
        //mMarkerRef = null;
    }

    /**
     * reference to the Marker on which it is opened. Null if none.
     * @return
     */
    public Marker getMarkerReference(){
        return mMarkerRef;
    }

    @Override public void onOpen(Object item) {
        super.onOpen(item);

        mMarkerRef = (Marker)item;
        if (mView==null) {
            Log.w(IMapView.LOGTAG, "Error trapped, MarkerInfoWindow.open, mView is null!");
            return;
        }

        TextView textView = (TextView)mView.findViewById(R.id.bubble_title);
        textView.setText("sdasdasd");

    }

    @Override public void onClose() {
        super.onClose();
        mMarkerRef = null;
        //by default, do nothing else
    }

}
