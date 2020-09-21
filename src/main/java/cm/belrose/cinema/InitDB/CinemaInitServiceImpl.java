package cm.belrose.cinema.InitDB;

import cm.belrose.cinema.dao.*;
import cm.belrose.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional //Cette annotation permet de resoudre le problème du Lazy initializer
public class CinemaInitServiceImpl implements ICinemaInitService {

    @Autowired
    private CategorieDao categorieDao;

    @Autowired
    private VilleDao villeDao;

    @Autowired
    private CinemaDao cinemaDao;

    @Autowired
    private SalleDao salleDao;

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private SeanceDao seanceDao;

    @Autowired
    private ProjectionDao projectionDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private FilmDao filmDao;

    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger").forEach(nameVille->{
            Ville ville=new Ville();
            ville.setName(nameVille);
            villeDao.save(ville);

        });

    }

    @Override
    public void initCinemas() {
        villeDao.findAll().forEach(ville -> {
            Stream.of("MegaRama","IMAX","FOUNDOUN","CHAHRAZAD","DAOULIZ").forEach(nameCinema->{
                Cinema cinema=new Cinema();
                cinema.setName(nameCinema);
                cinema.setNombreSalles(3+(int)(Math.random()*7));
                cinema.setVille(ville);
                cinemaDao.save(cinema);
            });
        });

    }

    @Override
    public void initSalles() {
        cinemaDao.findAll().forEach(cinema -> {
            for(int i=0;i<cinema.getNombreSalles();i++){
                Salle salle =new Salle();
                salle.setName("Salle "+(i+1));
                salle.setCinema(cinema);
                salle.setNumberPlace(15+(int)(Math.random()*20));
                salleDao.save(salle);
            }
        });

    }

    @Override
    public void initPlaces() {
        salleDao.findAll().forEach(salle -> {
            for(int i=0;i<salle.getNumberPlace();i++){
                Place place =new Place();
                place.setNumber(i+1);
                place.setSalle(salle);
                placeDao.save(place);
            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat df=new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","22:00").forEach(s->{
            Seance seance=new Seance();
            try {
                seance.setStartTime(df.parse(s));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            seanceDao.save(seance);
        });

    }

    @Override
    public void initCategories() {
        Stream.of("Action","D'horreur","Fiction","Comédie","Drame","Documentaire","Western").forEach(cat->{
            Categorie categorie=new Categorie();
            categorie.setName(cat);
            categorieDao.save(categorie);
        });


    }

    @Override
    public void initFilms() {
        double [] durees=new double[] {1,1.5,2,2.5,3};
        List<Categorie> categories=categorieDao.findAll();
        Stream.of("Seven","Fight Club","Driver","The Truman Show","Forest Gump").forEach(titreFilm->{
            Film film=new Film();
            film.setTitle(titreFilm);
            film.setDuration(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(titreFilm.replaceAll(" ","")+".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmDao.save(film);
        });

    }



    @Override
    public void initProjections() {
        double[] prices=new double[]{30,50,60,70,90,100};
        List<Film> films=filmDao.findAll();
        villeDao.findAll().forEach(ville->{
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    //Programmation aléatoire d'un film
                    int index=new Random().nextInt(films.size());
                    Film film=films.get(index);
                        seanceDao.findAll().forEach(seance -> {
                            Projection projection=new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionDao.save(projection);
                        });
                });
            });
        });

    }

    @Override
    public void initTickets() {
        projectionDao.findAll().forEach(p->{
            p.getSalle().getPlaces().forEach(place -> {
                Ticket ticket=new Ticket();
                ticket.setPlace(place);
                ticket.setPrice(p.getPrix());
                ticket.setProjection(p);
                ticket.setReserve(false);
                ticketDao.save(ticket);
            });
        });

    }
}
