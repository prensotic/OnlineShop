package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.entities.UserPersonalData;
import ru.loginov.dev.exceptions.userPersonalDataExceptions.UserPersonalDataAlreadyExistsException;
import ru.loginov.dev.exceptions.userPersonalDataExceptions.UserPersonalDataNotFound;
import ru.loginov.dev.repositories.UserPersonalDataRepository;

import java.util.List;

@Service
public class UserPersonalDataService {
    private final UserPersonalDataRepository userPersonalDataRepository;

    public UserPersonalDataService(UserPersonalDataRepository userPersonalDataRepository){
        this.userPersonalDataRepository = userPersonalDataRepository;
    }

    @Transactional
    public List<UserPersonalData> getAllUsersPersonalData(){
        return userPersonalDataRepository.getAllUsersPersonalData();
    }

    @Transactional
    public UserPersonalData findUserPersonalDataById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        UserPersonalData userPersonalData = userPersonalDataRepository.findUserPersonalDataById(id);

        if(userPersonalData == null)
            throw new UserPersonalDataNotFound("Персональные данные пользователя c ID: " + id + " не найдены");

        return userPersonalData;
    }

    @Transactional
    public UserPersonalData findUserPersonalDataByUserId(long userId){
        if(userId <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");

        UserPersonalData userPersonalData = userPersonalDataRepository.findUserPersonalDataByUserId(userId);

        if(userPersonalData == null)
            throw new UserPersonalDataNotFound(
                    "Персональные данные пользователя c ID учетной записи: " + userId + " не найдены");

        return userPersonalData;
    }

    @Transactional
    public void createUserPersonalData(User user, String firstName, String lastName, String phoneNumber){
        if(user == null) throw new IllegalArgumentException("Пользователь указан некорректно");

        if (userPersonalDataRepository.findUserPersonalDataByUserId(user.getId()) != null)
            throw new UserPersonalDataAlreadyExistsException("Персональные данные для пользователя уже существуют");

        if(firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("Имя пользователя указан некорректно");

        if(lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Фамилия пользователя указана некорректно");

        if(phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Телефон указан некорректно");

        UserPersonalData userPersonalData = new UserPersonalData();

        userPersonalData.setUser(user);
        userPersonalData.setFirstName(firstName);
        userPersonalData.setLastName(lastName);
        userPersonalData.setPhoneNumber(phoneNumber);

        userPersonalDataRepository.saveUserPersonalData(userPersonalData);
    }

    @Transactional
    public void updateUserPersonalData(long id, String firstName, String lastName, String phoneNumber){
        UserPersonalData userPersonalData = findUserPersonalDataById(id);

        if(firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("Имя пользователя указан некорректно");

        if(lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Фамилия пользователя указана некорректно");

        if(phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Телефон указан некорректно");

        userPersonalData.setFirstName(firstName);
        userPersonalData.setLastName(lastName);
        userPersonalData.setPhoneNumber(phoneNumber);
    }

    @Transactional
    public void deleteUserPersonalData(long id){
        findUserPersonalDataById(id);
        userPersonalDataRepository.deleteUserPersonalData(id);
    }
}