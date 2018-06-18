package com.example.root.dietlogging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private LiveData<List<User>> mUser;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mUser = mRepository.getUser();
    }

    LiveData<List<User>> getUser() { return mUser; }

    public void insert(User user) { mRepository.insert(user); }


}
