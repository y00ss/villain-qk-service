package io.quarkus.workshop.superheroes.villain.service;

import io.quarkus.workshop.superheroes.villain.entity.Villain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class VillainServiceImpl {

    @ConfigProperty(name = "level.multiplier", defaultValue = "1.0")
    double levelMultiplier;

    @Transactional(SUPPORTS)
    public Villain findVillainById(Long id) {
        return Villain.findById(id);
    }

    @Transactional(SUPPORTS)
    public List<Villain> findVillainsByName(String name) {
        return Villain.find("name = ?1", name).list();
    }

    public List<Villain> findAll() {
        return Villain.listAll();
    }

    public Villain createVillain(@Valid Villain villain) {

        villain.level = (int) Math.round(villain.level * levelMultiplier);
        Villain.persist(villain);

        return villain;
    }

    /*
     * @Transactional(SUPPORTS)
    public Villain findVillainByName(String name){
        return Villain.find("name", name);
    }
    * **/

    @Transactional(SUPPORTS)
    public Villain findRandomVillain() {
        Villain randomVillain = null;
        while (randomVillain == null) {
            randomVillain = Villain.findRandomVillain();
        }
        return randomVillain;
    }

    //todo fix.. check if exist
    public Villain updateVillain(@Valid Villain villain) {
        Villain updateVillain = Villain.findById(villain.id);

        updateVillain.name = villain.name;
        updateVillain.level = villain.level;
        updateVillain.otherName = villain.otherName;
        updateVillain.picture = villain.picture;
        updateVillain.powers = villain.powers;

        return updateVillain;
    }

    public void deleteVillainById(Long id) {
        Villain.deleteById(id);
    }
}
