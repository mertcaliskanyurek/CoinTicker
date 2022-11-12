package com.mertcaliskanyurek.cointicker.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


class Converters {
    @TypeConverter
    fun fromDateString(value: String?): Date? = value?.let {
        SimpleDateFormat.getDateTimeInstance().parse(it)
    }

    @TypeConverter
    fun dateToDateString(date: Date?): String? = date?.toString()

    @TypeConverter
    fun fromStringToDescription(descriptionString: String?): Description? = descriptionString?.let {
        val myType: Type = object : TypeToken<Description?>() {}.type
        return Gson().fromJson<Description>(it, myType)
    }

    @TypeConverter
    fun fromDescriptionToString(description: Description?): String? = description?.let {
        val gson = Gson()
        val type = object : TypeToken<Description?>() {}.type
        return gson.toJson(it, type)
    }

    @TypeConverter
    fun fromStringToLinks(linksString: String?): Links? = linksString?.let {
        val myType: Type = object : TypeToken<Links?>() {}.type
        return Gson().fromJson<Links>(it, myType)
    }

    @TypeConverter
    fun fromLinksToString(links: Links?): String? = links?.let {
        val gson = Gson()
        val type = object : TypeToken<Links?>() {}.type
        return gson.toJson(it, type)
    }

    @TypeConverter
    fun fromStringToImages(imagesString: String?): Images? = imagesString?.let {
        val myType: Type = object : TypeToken<Images?>() {}.type
        return Gson().fromJson<Images>(it, myType)
    }

    @TypeConverter
    fun fromImageToString(images: Images?): String? = images?.let {
        val gson = Gson()
        val type = object : TypeToken<Images?>() {}.type
        return gson.toJson(it, type)
    }

    @TypeConverter
    fun fromStringToDeveloperData(developerDataString: String?): DeveloperData? = developerDataString?.let {
        val myType: Type = object : TypeToken<DeveloperData?>() {}.type
        return Gson().fromJson<DeveloperData>(it, myType)
    }

    @TypeConverter
    fun fromDeveloperDataToString(developerData: DeveloperData?): String? = developerData?.let {
        val gson = Gson()
        val type = object : TypeToken<DeveloperData?>() {}.type
        return gson.toJson(it, type)
    }

    @TypeConverter
    fun fromStringToCommunityData(communityDataString: String?): CommunityData? = communityDataString?.let {
        val myType: Type = object : TypeToken<CommunityData?>() {}.type
        return Gson().fromJson<CommunityData>(it, myType)
    }

    @TypeConverter
    fun fromCommunityDataToString(communityData: CommunityData?): String? = communityData?.let {
        val gson = Gson()
        val type = object : TypeToken<CommunityData?>() {}.type
        return gson.toJson(it, type)
    }
}