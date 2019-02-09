package com.example.logonrmlocal.githubaac.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.example.logonrmlocal.githubaac.data.local.MyDatabase
import com.example.logonrmlocal.githubaac.data.local.dao.UserDao
import com.example.logonrmlocal.githubaac.data.remote.UserWebService
import com.example.logonrmlocal.githubaac.data.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule{

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MyDatabase{
        return  Room.databaseBuilder(
                application,
                MyDatabase::class.java,
                "mydatabase.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: MyDatabase): UserDao {
         return  database.userDao()
    }

    @Provides
    @Singleton
    fun provideExecutor():Executor{
        return  Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun  provideGson(): Gson{
        return  GsonBuilder().create()
    }


    @Provides
    @Singleton
     fun  provideretrofit(gson: Gson): Retrofit{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://api.github.com").build()
    }

    @Singleton
    @Provides
    fun provideUserWebService(retrofit: Retrofit): UserWebService{
        return  retrofit.create(UserWebService::class.java)
    }

    @Singleton
    @Provides
    fun  provideUserRepository(userWebService: UserWebService, userDao: UserDao, executor: Executor):
            UserRepository{
        return  UserRepository(userWebService, userDao, executor)
    }
}