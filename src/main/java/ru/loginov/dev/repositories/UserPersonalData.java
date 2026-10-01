package ru.loginov.dev.repositories;

public interface UserPersonalData {
    public UserPersonalData findUserPersonalDataById(long id);
    public void saveUserPersonalData(UserPersonalData userPersonalData);
    public void updateUserPersonalData(UserPersonalData userPersonalData);
    public void deleteUserPersonalData(long id);
}
