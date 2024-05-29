package com.example.imageapi01.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomEntity::class), version = 1)

abstract class ImageRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao() : RoomDao

    companion object {
        private const val DATABASE_NAME = "db_room"
        private var appDatabase : ImageRoomDatabase? = null

        fun getInstance(context : Context) : ImageRoomDatabase? {
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context,
                    ImageRoomDatabase::class.java,
                    DATABASE_NAME).
                    //마이그레이션이 실패할 때 db테이블 재생성, 데이터 사라질 수 있음
                fallbackToDestructiveMigration()
                    .build()

            }
            return appDatabase
        }


    }

}
