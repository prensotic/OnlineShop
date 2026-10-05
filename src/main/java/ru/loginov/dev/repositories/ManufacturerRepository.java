package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {
    List<Manufacturer> getAllManufacturer();
    Manufacturer findManufacturerById(long id);
    Manufacturer findManufacturerByName(String manufacturerName);
    boolean existsManufacturerName(String manufacturerName);
    boolean existsManufacturerNameAndIdNot(String manufacturerName, long id);
    void saveManufacturer(Manufacturer manufacturer);
    void updateManufacturer(Manufacturer manufacturer);
    void deleteManufacturer(long id);
}
