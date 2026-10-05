package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {
    List<Manufacturer> getAllManufacturer();
    Manufacturer findManufacturerById(long id);
    boolean existsManufacturerName(String manufacturerName);
    void saveManufacturer(Manufacturer manufacturer);
    void updateManufacturer(Manufacturer manufacturer);
    void deleteManufacturer(long id);
}
