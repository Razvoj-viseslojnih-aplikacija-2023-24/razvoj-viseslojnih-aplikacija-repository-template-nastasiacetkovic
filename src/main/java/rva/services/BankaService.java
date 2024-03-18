package rva.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rva.models.Banka;

@Service
public interface BankaService extends CrudService<Banka> {


	List<Banka> getBankasByNazivContainingIgnoreCase(String naziv);
}
