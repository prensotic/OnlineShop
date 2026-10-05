package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Manufacturer;
import ru.loginov.dev.exceptions.manufacturerException.ManufacturerAlreadyExistsException;
import ru.loginov.dev.exceptions.manufacturerException.ManufacturerNotFoundException;
import ru.loginov.dev.repositories.ManufacturerRepository;

import java.util.List;

@Service
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository){
        this.manufacturerRepository = manufacturerRepository;
    }

    @Transactional
    public List<Manufacturer> getAllManufacturers(){
        return manufacturerRepository.getAllManufacturer();
    }

    @Transactional
    public Manufacturer findManufacturerById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        Manufacturer manufacturer = manufacturerRepository.findManufacturerById(id);

        if(manufacturer == null) throw new ManufacturerNotFoundException("Производитель с ID: " + id + " не найден");

        return manufacturer;
    }

    @Transactional
    public Manufacturer findManufacturerByName(String manufacturerName){
        if(manufacturerName == null || manufacturerName.isBlank())
            throw new IllegalArgumentException("Имя производителя указано некорректно");

        Manufacturer manufacturer = manufacturerRepository.findManufacturerByName(manufacturerName);

        if(manufacturer == null) throw new ManufacturerNotFoundException("Производитель с именем: " + manufacturerName + " не найден");

        return manufacturer;
    }

    @Transactional
    public void createManufacturer(String manufacturerName, String manufacturerDescription, String country){
        if(manufacturerName == null || manufacturerName.isBlank())
            throw new IllegalArgumentException("Имя производителя указано некорректно");

        if(manufacturerDescription == null || manufacturerDescription.isBlank())
            throw new IllegalArgumentException("Описание производителя указано некорректно");

        if(country == null || country.isBlank())
            throw new IllegalArgumentException("Страна производителя указана некорректно");

        if(manufacturerRepository.existsManufacturerName(manufacturerName))
            throw new ManufacturerAlreadyExistsException(
                    "Производитель с именем: " + manufacturerName + " уже существует");


        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerName(manufacturerName);
        manufacturer.setManufacturerDescription(manufacturerDescription);
        manufacturer.setCountry(country);

        manufacturerRepository.saveManufacturer(manufacturer);
    }

    @Transactional
    public void updateManufacturer(long id, String manufacturerName, String manufacturerDescription, String country){
        Manufacturer manufacturer = findManufacturerById(id);

        if(manufacturerName == null || manufacturerName.isBlank())
            throw new IllegalArgumentException("Имя производителя указано некорректно");

        if(manufacturerDescription == null || manufacturerDescription.isBlank())
            throw new IllegalArgumentException("Описание производителя указано некорректно");

        if(country == null || country.isBlank())
            throw new IllegalArgumentException("Страна производителя указана некорректно");

        if (manufacturerRepository.existsManufacturerNameAndIdNot(manufacturerName, id))
            throw new ManufacturerAlreadyExistsException(
                    "Производитель с именем: " + manufacturerName + " уже существует");

        manufacturer.setManufacturerName(manufacturerName);
        manufacturer.setManufacturerDescription(manufacturerDescription);
        manufacturer.setCountry(country);
    }

    @Transactional
    public void deleteManufacturer(long id){
        findManufacturerById(id);
        manufacturerRepository.deleteManufacturer(id);
    }
}