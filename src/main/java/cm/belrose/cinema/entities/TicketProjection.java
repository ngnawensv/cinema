package cm.belrose.cinema.entities;

import org.springframework.data.rest.core.config.Projection;
/*
Cette interface permet de faire une projection sur la classe Projection.
Celà evite de faire des sous-requetes au niveau client pour avoir les  films, seances, tickets, etc.
visualiser le resultat en tapant sur le navigateur: localhost:8080/projections/id?projection=p1
 */
/*Une projection permet de personnaliser Spring Data Rest c'est-à-dire que quand une entité est relation et
 * quand l'on se rend sur une entité il veut qu'il lui ramene les données en relation avec cette entité, il est
 * pratique de faire ça avec les projections
 **/
@Projection(name = "ticketProj", types = Ticket.class)
public interface TicketProjection {
    public Long getId();

    public String getNameClient();

    public double getPrice();

    public Integer getCodePayement();

    public boolean isReserve();

    public Place getPlace();
}
