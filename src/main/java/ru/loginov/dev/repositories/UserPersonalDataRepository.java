package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.UserPersonalData;

import java.util.List;

public interface UserPersonalDataRepository {
    List<UserPersonalData> getAllUsersPersonalData();
    UserPersonalData findUserPersonalDataById(long id);
    UserPersonalData findUserPersonalDataByUserId(long userId);
    void saveUserPersonalData(UserPersonalData userPersonalData);
    void updateUserPersonalData(UserPersonalData userPersonalData);
    void deleteUserPersonalData(long id);
}
