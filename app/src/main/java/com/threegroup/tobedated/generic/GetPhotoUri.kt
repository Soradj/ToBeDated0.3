package com.threegroup.tobedated.generic

import android.content.Context
import android.util.Log
import com.threegroup.tobedated.R
import kotlin.random.Random

fun getPhotoUri(ethnicity: String, gender: String, context: Context): String {
    val resourceId = when {
        ethnicity == "Black/African Descent" && gender == "Male" -> R.drawable._blackmale
        ethnicity == "Black/African Descent" && gender == "Female" -> R.drawable._blackfemale
        ethnicity == "Black/African Descent" &&  gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._blackmale
        }else{
            R.drawable._blackfemale
        }
        ethnicity == "East Asian" && gender == "Male" -> R.drawable._eastmale
        ethnicity == "East Asian" && gender == "Female" -> R.drawable._eastfemale
        ethnicity == "East Asian" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._eastmale
        }else{
            R.drawable._eastfemale
        }
        ethnicity == "Hispanic/Latino" && gender == "Male" -> R.drawable._hispanicmale
        ethnicity == "Hispanic/Latino" && gender == "Female" -> R.drawable._hispanicfemale
        ethnicity == "Hispanic/Latino" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._hispanicmale
        }else{
            R.drawable._hispanicfemale
        }
        ethnicity == "Middle Eastern" && gender == "Male" -> R.drawable._middlemale
        ethnicity == "Middle Eastern" && gender == "Female" -> R.drawable._middlefemale
        ethnicity == "Middle Eastern" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._middlemale
        }else{
            R.drawable._middlefemale
        }
        ethnicity == "Native American" && gender == "Male" -> R.drawable._nativemale
        ethnicity == "Native American" && gender == "Female" -> R.drawable._nativefemale
        ethnicity == "Native American" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._nativemale
        }else{
            R.drawable._nativefemale
        }
        ethnicity == "Pacific Islander" && gender == "Male" -> R.drawable._pacificmale
        ethnicity == "Pacific Islander" && gender == "Female" -> R.drawable._pacificfemale
        ethnicity == "Pacific Islander" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._pacificmale
        }else{
            R.drawable._pacificfemale
        }
        ethnicity == "South Asian" && gender == "Male" -> R.drawable._southmale
        ethnicity == "South Asian" && gender == "Female" -> R.drawable._southfemale
        ethnicity == "South Asian" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._southmale
        }else{
            R.drawable._southfemale
        }
        ethnicity == "Southeast Asian" && gender == "Male" -> R.drawable._southeastmale
        ethnicity == "Southeast Asian" && gender == "Female" -> R.drawable._southeastfemale
        ethnicity == "Southeast Asian" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._southeastmale
        }else{
            R.drawable._southeastfemale
        }
        ethnicity == "White/Caucasian" && gender == "Male" -> R.drawable._whitemale
        ethnicity == "White/Caucasian" && gender == "Female" -> R.drawable._whitefemale
        ethnicity == "White/Caucasian" && gender == "Other" -> if(Random.nextBoolean()){
            R.drawable._whitemale
        }else{
            R.drawable._whitefemale
        }
        else -> R.drawable._hispanicfemale // Default resource ID
    }
    Log.d("getDrawableResource", "Resource ID: $resourceId")
    val uriString = "android.resource://${context.packageName}/$resourceId"
    Log.d("getResourceUri", "URI: $uriString")
    return uriString
}