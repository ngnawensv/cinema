package cm.belrose.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

/*
Un projection evite de faire plusieurs requetes pour recuperer une information au niveau du front end
 */
/*
Cette interface permet de faire une projection sur la classe Projection.
Celà evite de faire des sous-requetes au niveau client pour avoir les  films, seances, tickets, etc.
visualiser le resultat en tapant sur le navigateur: localhost:8080/projections/id?projection=p1
 */
/*Une projection permet de personnaliser Spring Data Rest c'est-à-dire que quand une entité est relation et
  quand l'on se rend sur une entité il veut qu'il lui ramene les données en relation avec cette entité, il est
  pratique de faire ça avec les projections
 */
@Projection(name = "p1", types = {cm.belrose.cinema.entities.Projection.class})
public interface ProjectionProj {
    public Long getId();

    public Date getDateProjection();

    public double getPrix();

    public Salle getSalle();

    public Film getFilm();

    public Seance getSeance();

    public Collection<Ticket> getTickets();

}
