package com.example.root.dietlogging.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.root.dietlogging.Entities.User;
import com.example.root.dietlogging.Repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private LiveData<List<User>> mUser;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mUser = mRepository.getUser();
    }

    public LiveData<List<User>> getUser() { return mUser; }

    public void insert(User user) { mRepository.insert(user); }

    public void update(User user) { mRepository.update(user); }


}
