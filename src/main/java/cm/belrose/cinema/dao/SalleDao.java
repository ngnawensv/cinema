package cm.belrose.cinema.dao;

import cm.belrose.cinema.entities.Cinema;
import cm.belrose.cinema.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/*@RepositoryRestResource est un annotation permettant de rendre accessible,
via une APIREST, toutes les methodes heritées de l'interface JpaRepository*/
@RepositoryRestResource
@CrossOrigin("*")
public interface SalleDao extends JpaRepository<Salle, Long> {
}
