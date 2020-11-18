package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.lifecycle.*

class MyViewModel(application: Application, handle: SavedStateHandle): AndroidViewModel(application) {
    /* If multiple TextViews exist and need to display different contents at the same time,
    * each TextView should hold its own variables such as handles,
    * instead of sharing the same variables and strings resource especially, otherwise,
    * when one of them changes its own contents, all the others will also
    * change what they are displaying in the screen
    * */
    private val hdlA = handle
    private val hdlB = handle
    //use different handle getters though getting handle from the same place
    
    private val keyA = getApplication<Application>().resources.getString(R.string.InitA)
    private val keyB = getApplication<Application>().resources.getString(R.string.InitB)
    
    private val AshpName = getApplication<Application>().resources.getString(R.string.ScoreA)
    private var Ashp =
        getApplication<Application>().getSharedPreferences(AshpName, Context.MODE_PRIVATE)
    
    private val BshpName = getApplication<Application>().resources.getString(R.string.ScoreB)
    private var Bshp =
        getApplication<Application>().getSharedPreferences(BshpName, Context.MODE_PRIVATE)
    //set different strings resources to create own sharedpreferences files and data

    init {
        if (!hdlA.contains(keyA) || !hdlB.contains(keyB)){
            hdlA.set(keyA, Ashp.getInt(keyA, 0))
            hdlB.set(keyB, Bshp.getInt(keyB, 0))
        }
    }//initialize handle with sharedpreferences
    
    fun getScoreA(): LiveData<Int> {
        return hdlA.getLiveData(keyA);
    }
    fun getScoreB(): LiveData<Int> {
        return hdlB.getLiveData(keyB);
    }//get function is necessary or nothing could be passed to UI
    
    private fun save() {
        val editorA = Ashp.edit()
        editorA.putInt(keyA, getScoreA().value!!)
        editorA.apply()
        
        val editorB = Bshp.edit()
        editorB.putInt(keyB, getScoreB().value!!)
        editorB.apply()
    }
    
    private var aBack = 0
    private var bBack = 0
    
    fun add_A(p: Int){
        aBack = getScoreA().value!!
        hdlA.set(keyA,getScoreA().value!!.plus(p))
        save();
    }
    
    fun add_B(p: Int){
        bBack = getScoreB().value!!
        hdlB.set(keyB,getScoreB().value!!.plus(p))
        save();
    }
    
    fun undo(){
        hdlA.set(keyA,aBack)
        hdlB.set(keyB,bBack)
        save()
    }
    
    fun clear(p:Int){
        aBack = getScoreA().value!!
        bBack = getScoreB().value!!
        hdlA.set(keyA,p)
        hdlB.set(keyB,p)
        save()
    }
    //Something strange happened to function clear(),
    //setting keys to '0' directly in hdl.set() will cause problem,
    //also passing '0' from a variable created in MyViewModel will,
    //it can only set scores to 0 without problems in layout files
}