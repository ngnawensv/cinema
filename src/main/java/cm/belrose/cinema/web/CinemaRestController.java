package cm.belrose.cinema.web;

import cm.belrose.cinema.dao.FilmDao;
import cm.belrose.cinema.dao.TicketDao;
import cm.belrose.cinema.entities.Film;
import cm.belrose.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {

    @Autowired
    private FilmDao filmDao;
    @Autowired
    private TicketDao ticketDao;

   /* @GetMapping("/listFilms")
    public List<Film> films(){
        return filmDao.findAll();
    }*/

   @GetMapping(path ="/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)// spécifie que les données retournées sont au format binaire
   public byte[] image(@PathVariable(name = "id") Long id) throws Exception{
      Film f=filmDao.findById(id).get();
      String photoName=f.getPhoto();
       File file=new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
       Path path= Paths.get(file.toURI());
       return Files.readAllBytes(path);
   }

   /* Pour etre professionnel ce traitement doit etre faite dans une couche service*/
   @PostMapping("/payerTickets")
   public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
       List<Ticket> listTickets=new ArrayList<>();
       ticketForm.getTickets().forEach(idTicket->{
           Ticket ticket=ticketDao.findById(idTicket).get();
           ticket.setNameClient(ticketForm.getNameClient());
           ticket.setCodePayement(ticketForm.getCodePayement());
           ticket.setReserve(true);
           ticketDao.save(ticket);
           listTickets.add(ticket);
       });
       return listTickets;

   }

}

class TicketForm{
    private String nameClient;
    private int codePayement;
    private List<Long> tickets=new ArrayList<>();

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public int getCodePayement() {
        return codePayement;
    }

    public void setCodePayement(int codePayement) {
        this.codePayement = codePayement;
    }

    public List<Long> getTickets() {
        return tickets;
    }

    public void setTickets(List<Long> tickets) {
        this.tickets = tickets;
    }
}
