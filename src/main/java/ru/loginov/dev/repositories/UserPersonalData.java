package ru.loginov.dev.repositories;

import java.util.List;

public interface UserPersonalData {
    public List<UserPersonalData> getAllUsersPersonalData();
    public UserPersonalData findUserPersonalDataById(long id);
    public UserPersonalData findUserPersonalDataByUserId(long userId);
    public void saveUserPersonalData(UserPersonalData userPersonalData);
    public void updateUserPersonalData(UserPersonalData userPersonalData);
    public void deleteUserPersonalData(long id);
}
