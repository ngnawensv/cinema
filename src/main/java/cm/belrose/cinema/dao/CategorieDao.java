package cm.belrose.cinema.dao;

import cm.belrose.cinema.entities.Categorie;
import cm.belrose.cinema.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
/*@RepositoryRestResource est un annotation permettant de rendre accessible,
via une APIREST, toutes les methodes heritées de l'interface JpaRepository*/
@RepositoryRestResource
public interface CategorieDao extends JpaRepository<Categorie, Long> {
}
