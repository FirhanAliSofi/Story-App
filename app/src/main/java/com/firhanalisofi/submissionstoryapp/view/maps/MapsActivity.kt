package com.firhanalisofi.submissionstoryapp.view.maps

import android.content.ContentValues
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.firhanalisofi.submissionstoryapp.R
import com.firhanalisofi.submissionstoryapp.databinding.ActivityMapsBinding
import com.firhanalisofi.submissionstoryapp.view.ViewModelFactory
import com.firhanalisofi.submissionstoryapp.data.response.ListStoryItem
import com.firhanalisofi.submissionstoryapp.helper.Result
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var viewModelFactory: ViewModelFactory
    private val mapsViewModel: MapsViewModel by viewModels { viewModelFactory }
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMapFragment()
        setViewModel()
        setActionBar()
    }

    private fun setMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setViewModel(){
        viewModelFactory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun setActionBar(){
        supportActionBar?.title = "Maps"
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        getStoryLocation(googleMap)

        setMapStyle()
    }

    private fun setMapStyle() {
        try{
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style))
            if(!success){
                Log.e(ContentValues.TAG,"Style parsing failed")
            }
        }catch (e: Resources.NotFoundException){
            Log.e(ContentValues.TAG,"Can't find style. Error: ", e)
        }
    }

    private fun getStoryLocation(googleMap: GoogleMap){
        mapsViewModel. getStoryMap().observe(this){
            if (it != null){
                if (it is Result.Error) {
                    Toast.makeText(this, "There is an error", Toast.LENGTH_SHORT).show()
                }
                else if (it is Result.Success) {
                    showMarkerStory(it.data.listStory, googleMap)
                }
            }
        }
    }

    private fun showMarkerStory(listStory: List<ListStoryItem>, googleMap: GoogleMap){
        listStory.forEach {
            val latLng = LatLng(it.lat, it.lon)
            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(it.name)
            )
            boundsBuilder.include(latLng)
        }
    }
}