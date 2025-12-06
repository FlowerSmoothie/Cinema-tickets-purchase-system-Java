package upping;

import com.google.gson.Gson;
import dao.TicketDAO;
import entities.*;
import enums.OrderStatuses;
import enums.Responses;
import enums.TicketStatuses;
import enums.UserStatuses;
import services.*;
import stuff.NewUserData;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server implements Runnable {

    private Socket clientSocket;
    private ClientRequest request;
    private ServerResponse response;
    private Gson gson;
    private BufferedReader in;
    private PrintWriter out;

    private CinemaService cinemaService = new CinemaService();
    private HallService hallService = new HallService();
    private MovieService movieService = new MovieService();
    private OrderService orderService = new OrderService();
    private ReviewService reviewService = new ReviewService();
    private SeatService seatService = new SeatService();
    private SeanceService seanceService = new SeanceService();
    private StatisticService statisticService = new StatisticService();
    private TicketService ticketService = new TicketService();
    private UserService userService = new UserService();

    public Server(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        response = new ServerResponse();
        request = new ClientRequest();
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run()
    {
        ObjectInputStream sois   = null;
        ObjectOutputStream soos   = null;
        User user;
        NewUserData newUserData;
        Cinema cinema;
        Seat seat;
        Hall hall;
        Movie movie;
        Seance seance;
        Review review;
        Statistic statistic;
        Date date;
        Order order;
        int result;

        boolean first = true;
        try
        {
            while(clientSocket.isConnected())
            {
                if(first) {
                    sois = new ObjectInputStream(clientSocket.getInputStream());
                    soos = new ObjectOutputStream(clientSocket.getOutputStream());
                    first = false;
                }
                request = (ClientRequest)sois.readObject();
                switch(request.getRequest())
                {
                    case REGISTER:
                        try
                        {
                            user = (User)request.getObject();
                            if (!userService.ifTheresUserWithSameLogin(user.getLogin()))
                            {
                                userService.addEntity(user);
                                response = new ServerResponse(Responses.OKAY, "Registration successful.", null);
                                System.out.println("Registration of a user with the " + user.getLogin() + " username is successfull.");
                            }
                            else
                                response = new ServerResponse(Responses.ERROR, "There is already a user with such login.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to register.", null);
                        }
                        break;
                    case LOGIN:
                        try
                        {
                            user = (User)request.getObject();
                            user = userService.isLoginAndPasswordEqual(user.getLogin(), user.getPassword());
                            if(user == null)
                                response = new ServerResponse(Responses.ERROR, "Invalid login or password.", null);
                            else
                            {
                                System.out.println(user.getRole());
                                response = new ServerResponse(Responses.OKAY, "Authorisation successful.", user);
                                System.out.println("User with the " + user.getLogin() + " login has authorised.");
                            }
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to log in.", null);
                        }
                        break;
                    case CHANGE_LOGIN:
                        try
                        {
                            result = userService.changeLogin((NewUserData)request.getObject());
                            switch(result)
                            {
                                case 0:
                                    response = new ServerResponse(Responses.OKAY, "Changed login successfully.", null);
                                    break;
                                case 1:
                                    response = new ServerResponse(Responses.ERROR, "There is already a user with such login.", null);
                                    break;
                                case 2:
                                    response = new ServerResponse(Responses.ERROR, "Wrong password.", null);
                                    break;
                            }
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to change login.", null);
                        }
                        break;
                    case CHANGE_PASSWORD:
                        try
                        {
                            result = userService.changePassword((NewUserData)request.getObject());
                            switch(result)
                            {
                                case 0:
                                    response = new ServerResponse(Responses.OKAY, "Changed password successfully.", null);
                                    break;
                                case 1:
                                    response = new ServerResponse(Responses.ERROR, "Wrong password.", null);
                                    break;
                            }
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to change password.", null);
                        }
                        break;
                    case VIEW_ORDER_LIST:
                        //user = (User)sois.readObject();
                        try
                        {
                            int id = (Integer)request.getObject();
                            List<Order> orders = orderService.findAllOrdersFromCertainUser(id);
                            response = new ServerResponse(Responses.OKAY, "Order list for the user.", orders);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show orders.", null);
                        }
                        break;
                    case DEACTIVATE_ACCOUNT:
                        try
                        {
                            int id = (Integer)request.getObject();
                            result = userService.deactivateAccount(id);
                            switch (result)
                            {
                                case 0:
                                    response = new ServerResponse(Responses.OKAY, "Deactivated successfully.", null);
                                    break;
                                case 1:
                                    response = new ServerResponse(Responses.ERROR, "Wrong password.", null);
                                    break;
                            }
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to deactivate account.", null);
                        }
                        break;
                    case ADD_CINEMA:
                        try
                        {
                            cinema = (Cinema)request.getObject();
                            if(cinemaService.isCinemaUnique(cinema))
                            {
                                cinemaService.addEntity(cinema);
                                response = new ServerResponse(Responses.OKAY, "Added the cinema.", null);
                            }
                            else
                                response = new ServerResponse(Responses.ERROR, "There is already such a cinema.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to add cinema.", null);
                        }
                        break;
                    case UPDATE_SEAT:
                        try
                        {
                            seat = (Seat)request.getObject();
                            seatService.updateEntity(seat);
                            response = new ServerResponse(Responses.OKAY, "Place updated.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update the place.", null);
                        }
                        break;
                    case ADD_HALL:
                        try
                        {
                            hall = (Hall)request.getObject();
                            int id = hall.getCinemaID();
                            Cinema c = cinemaService.findCinemaEntity(id);
                            c.setCountOfHalls(c.getCountOfHalls() + 1);
                            cinemaService.updateEntity(c);
                            hallService.addEntityID(hall);
                            List<Hall> halls = hallService.getHallsByCinemaID(id);
                            id = halls.get(halls.size() - 1).getID();
                            response = new ServerResponse(Responses.OKAY, "Hall added.", id);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to add hall.", null);
                        }
                        break;
                    case ADD_MOVIE:
                        try
                        {
                            movie = (Movie)request.getObject();
                            movieService.addEntity(movie);
                            response = new ServerResponse(Responses.OKAY, "Movie added.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to add movie.", null);
                        }
                        break;
                    case ADD_SEANCE:
                        try
                        {
                            seance = (Seance) request.getObject();
                            seanceService.addNewSeance(seance);
                            List<Seance> seances = seanceService.getAllSeances();
                            int id = seances.get(seances.size() - 1).getID();
                            response = new ServerResponse(Responses.OKAY, "Showing added.", id);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            response = new ServerResponse(Responses.ERROR, "Server failed to add showing.", null);
                        }
                        break;
                    case UPDATE_CINEMA:
                        try
                        {
                            cinema = (Cinema)request.getObject();
                            cinemaService.updateEntity(cinema);
                            response = new ServerResponse(Responses.OKAY, "Cinema updated.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update cinema.", null);
                        }
                        break;
                    case UPDATE_HALL:
                        try
                        {
                            hall = (Hall)request.getObject();
                            hallService.updateEntity(hall);
                            response = new ServerResponse(Responses.OKAY, "Hall updated.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update hall.", null);
                        }
                        break;
                    case UPDATE_MOVIE:
                        try
                        {
                            movie = (Movie)request.getObject();
                            movieService.updateEntity(movie);
                            response = new ServerResponse(Responses.OKAY, "Movie updated.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update movie.", null);
                        }
                        break;
                    case UPDATE_SEANCE:
                        try
                        {
                            seance = (Seance)request.getObject();
                            seanceService.updateEntity(seance);
                            response = new ServerResponse(Responses.OKAY, "Showing updated.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update showing.", null);
                        }
                        break;
                    case DELETE_REVIEW:
                        try
                        {
                            review = (Review)request.getObject();
                            int id = review.getMovieID();
                            reviewService.deleteEntity(review);
                            float rating = reviewService.countMovieRatingByReviews(id);
                            movie = movieService.findEntity(id);
                            movie.setRating(rating);
                            movieService.updateEntity(movie);
                            response = new ServerResponse(Responses.OKAY, "Review deleted.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to delete review.", null);
                        }
                        break;
                    case RESERVE_TICKET_WINDOW_SHOW:
                        try
                        {
                            seance = (Seance)request.getObject();
                            List<Ticket> tickets = ticketService.findTicketsToSeance(seance.getID());
                            response = new ServerResponse(Responses.OKAY, "Tickets shown.", tickets);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show tickets.", null);
                        }
                        break;
                    case PAY_ORDER:
                        try
                        {
                            int id = (Integer)request.getObject();
                            List<Order> orders = OrderService.findAllRecordsByOrderID(id);
                            for(Order o: orders)
                            {
                                o.setStatus(OrderStatuses.PAYED);
                                orderService.updateEntity(o);
                            }

                            response = new ServerResponse(Responses.OKAY, "Order paid.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to pay order.", null);
                        }
                        break;
                    case CANCEL_ORDER:
                        try
                        {
                            int id = (Integer)request.getObject();
                            List<Order> orders = OrderService.findAllRecordsByOrderID(id);
                            Statistic stat = new Statistic();
                            boolean isFirst = true;
                            int stand = 0, comf = 0, VIP = 0;
                            for(Order o: orders)
                            {
                                o.setStatus(OrderStatuses.CANCELLED);
                                orderService.updateEntity(o);
                                Ticket t = ticketService.findTicketByID(o.getTicket());
                                t.setStatus(1);
                                if(isFirst) { stat = statisticService.findForSeance(t.getSeanceID()); isFirst = false; }
                                ticketService.updateEntity(t);
                                switch(t.getType())
                                {
                                    case 0: { stand++; break; }
                                    case 1: { comf++; break; }
                                    case 2: { VIP++; break; }
                                }
                            }
                            stat.setStandartTicketsSold(stat.getStandartTicketsSold() - stand);
                            stat.setComfortTicketsSold(stat.getComfortTicketsSold() - comf);
                            stat.setVIPTicketsSold(stat.getVIPTicketsSold() - VIP);
                            statisticService.updateEntity(stat);
                            response = new ServerResponse(Responses.OKAY, "Order cancelled.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to cancel order.", null);
                        }
                        break;
                    case SHOW_ORDERS:
                        try
                        {
                            List<Order> orders = orderService.findAllEntities();
                            response = new ServerResponse(Responses.OKAY, "Order list shown.", orders);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show orders.", null);
                        }
                        break;
                    case SHOW_REVIEWS:
                        try
                        {
                            List<Review> reviews = reviewService.findAllEntities();
                            response = new ServerResponse(Responses.OKAY, "Review list shown.", reviews);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show reviews.", null);
                        }
                        break;
                    case SHOW_SEANCES:
                        try
                        {
                            List<Seance> seances = seanceService.getAllSeances();
                            response = new ServerResponse(Responses.OKAY, "Showings list shown.", seances);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show showings.", null);
                        }
                        break;
                    case SHOW_MOVIES:
                        try
                        {
                            List<Movie> movies = movieService.findAllEntities();
                            response = new ServerResponse(Responses.OKAY, "Movie list shown.", movies);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show movies.", null);
                        }
                        break;
                    case SHOW_USERS:
                        try
                        {
                            List<User> users = userService.findAllUserEntities();
                            response = new ServerResponse(Responses.OKAY, "User list shown.", users);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show users.", null);
                        }
                        break;
                    case SHOW_CINEMAS:
                        try
                        {
                            List<Cinema> cinemas = cinemaService.findAllCinemaEntities();
                            response = new ServerResponse(Responses.OKAY, "Cinema list shown.", cinemas);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show cinemas.", null);
                        }
                        break;
                    case SHOW_HALLS:
                        try
                        {
                            List<Hall> halls = hallService.findAllHallEntities();
                            response = new ServerResponse(Responses.OKAY, "Hall list shown.", halls);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show halls.", null);
                        }
                        break;
                    case SHOW_STATISTICS:
                        try
                        {
                            int id = (Integer)request.getObject();
                            Statistic stat = statisticService.findForSeance(id);
                            response = new ServerResponse(Responses.OKAY, "Statistic shown.", stat);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show statistic.", null);
                        }
                        break;
                    case SHOW_TICKETS:
                        try
                        {
                            List<Ticket> tickets = ticketService.findAllEntities();
                            response = new ServerResponse(Responses.OKAY, "Ticket list shown.", tickets);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show tickets.", null);
                        }
                        break;
                    case SEND_REVIEW:
                        try
                        {
                            review = (Review) request.getObject();
                            reviewService.addEntity(review);
                            int id = review.getMovieID();
                            float rating = reviewService.countMovieRatingByReviews(id);
                            movie = movieService.findMovieEntity(id);
                            movie.setRating(rating);
                            movieService.updateEntity(movie);
                            response = new ServerResponse(Responses.OKAY, "Review added.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to add review.", null);
                        }
                        break;
                    case UPDATE_USER:
                        try
                        {
                           user = (User) request.getObject();
                           if(user.getLogin().equals("admin")) throw new Exception();
                           userService.updateEntity(user);
                           response = new ServerResponse(Responses.OKAY, "User updated.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update user.", null);
                        }
                        break;
                    case CINEMA_HALL_COUNT:
                        try
                        {
                            int id = (Integer) request.getObject();
                            int count = hallService.getHallCountFromCinema(id);
                            response = new ServerResponse(Responses.OKAY, "Data shown.", count);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show data about cinema hall count.", null);
                        }
                        break;
                    case SEATS_FROM_HALL:
                        try
                        {
                            int id = (Integer)request.getObject();
                            List<Seat> seats = SeatService.getSeatsFromHall(id);
                            response = new ServerResponse(Responses.OKAY, "Data shown.", seats);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show data about seats.", null);
                        }
                        break;
                    case UPDATE_SEAT_LIST:
                        try
                        {
                            List<Seat> seats = (List<Seat>)request.getObject();
                            SeatService.updateSeatsList(seats);
                            response = new ServerResponse(Responses.OKAY, "Data updated.", seats);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to update data about seat list.", null);
                        }
                        break;
                    case SHOW_USER:
                        try
                        {
                            int id = (Integer)request.getObject();
                            user = userService.findEntity(id);
                            response = new ServerResponse(Responses.OKAY, "User data shown.", user);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show user data.", null);
                        }
                        break;
                    case SHOW_ACTUAL_SEANCES:
                        try
                        {
                            LocalDateTime lDate = (LocalDateTime)request.getObject();
                            List<Seance> seances = seanceService.findAllSeancesAvailable(lDate);
                            response = new ServerResponse(Responses.OKAY, "Showings data shown.", seances);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show showings data.", null);
                        }
                        break;
                    case VIEW_RECORDS_FOR_ORDERS:
                        try
                        {
                            int orderID = (Integer) request.getObject();
                            List<Order> orders = OrderService.findAllRecordsByOrderID(orderID);
                            response = new ServerResponse(Responses.OKAY, "Orders shown.", orders);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to show orders.", null);
                        }
                        break;
                    case GET_SEATS_FROM_ORDER:
                        try
                        {
                            List<Order> orders = (List<Order>) request.getObject();
                            List<Ticket> tickets = ticketService.findTicketsToOrders(orders);
                            List<Seat> seats = seatService.findSeatcByTickets(tickets);
                            response = new ServerResponse(Responses.OKAY, "Seats shown.", seats);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            response = new ServerResponse(Responses.ERROR, "Server failed to show seat data freom order.", null);
                        }
                        break;
                    case SHOW_SEANCE:
                        try
                        {
                            int id = (Integer) request.getObject();
                            Seance s = seanceService.findEntity(id);
                            response = new ServerResponse(Responses.OKAY, "Showing found.", s);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find showing.", null);
                        }
                        break;
                    case SHOW_TICKET:
                        try
                        {
                            int id = (Integer) request.getObject();
                            Ticket t = ticketService.findEntity(id);
                            response = new ServerResponse(Responses.OKAY, "Ticket found.", t);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find ticket.", null);
                        }
                        break;
                    case GET_TICKETS_BY_IDS:
                        try
                        {
                            List<Integer> ids = (List<Integer>)request.getObject();
                            List<Ticket> t = ticketService.findTicketsByID(ids);
                            response = new ServerResponse(Responses.OKAY, "Tickets found.", t);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            response = new ServerResponse(Responses.ERROR, "Server failed to find tickets.", null);
                        }
                        break;
                    case GET_HALLS_BY_IDS:
                        try
                        {
                            List<Integer> ids = (List<Integer>)request.getObject();
                            List<Hall> t = hallService.getHallsByIDs(ids);
                            response = new ServerResponse(Responses.OKAY, "Halls found.", t);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find halls.", null);
                        }
                        break;
                    case GET_SEANCES_BY_IDS:
                        try
                        {
                            List<Integer> ids = (List<Integer>)request.getObject();
                            List<Hall> t = hallService.getHallsByIDs(ids);
                            response = new ServerResponse(Responses.OKAY, "Showings found.", t);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find showings.", null);
                        }
                        break;
                    case ADD_TICKETS:
                        try
                        {
                            List<Ticket> tickets = (List<Ticket>)request.getObject();
                            ticketService.addTickets(tickets);

                            Seance s = seanceService.getSeanceByID(tickets.get(0).getSeanceID());

                            Statistic stat = new Statistic();
                            stat.setMovieID(tickets.get(0).getMovieID());
                            stat.setSeanceID(s.getID());
                            stat.setStandartTicketsSold(0);
                            stat.setComfortTicketsSold(0);
                            stat.setVIPTicketsSold(0);

                            int sta = 0, com = 0, VIP = 0;
                            for(Ticket t: tickets)
                                switch(t.getType())
                                {
                                    case 0: { sta++; break; }
                                    case 1: { com++; break; }
                                    case 2: { VIP++; break; }
                                }
                            stat.setStandartTicketsTotal(sta);
                            stat.setComfortTicketsTotal(com);
                            stat.setVIPTicketsTotal(VIP);
                            stat.setStandartTicketsPrice(s.getNormal());
                            stat.setComfortTicketsPrice(s.getComfort());
                            stat.setVIPTicketsPrice(s.getVIP());
                            statisticService.addEntity(stat);

                            response = new ServerResponse(Responses.OKAY, "Tickets added.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to add tickets.", null);
                        }
                        break;
                    case ADD_SEATS:
                        try
                        {
                            List<Seat> seats = (List<Seat>)request.getObject();
                            seatService.addSeats(seats);
                            response = new ServerResponse(Responses.OKAY, "Seats added.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to add seats.", null);
                        }
                        break;
                    case SHOW_SEANCES_BY_IDS:
                        try
                        {
                            List<Integer> seances = (List<Integer>)request.getObject();
                            List<Seance> s = seanceService.getSeancesByIDs(seances);
                            response = new ServerResponse(Responses.OKAY, "Showings found.", s);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find showings.", null);
                        }
                        break;
                    case SHOW_HALLS_FROM_CINEMA:
                        try
                        {
                            int id = (Integer)request.getObject();
                            List<Hall> s = hallService.getShowableHallsByCinemaID(id);
                            response = new ServerResponse(Responses.OKAY, "Halls found.", s);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to found halls.", null);
                        }
                        break;
                    case DELETE_MOVIE:
                        try
                        {
                            Movie m = (Movie)request.getObject();
                            movieService.deleteMovie(m);
                            response = new ServerResponse(Responses.OKAY, "Movie deleted.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to delete movie.", null);
                        }
                        break;
                    case DELETE_CINEMA:
                        try
                        {
                            Cinema c = (Cinema)request.getObject();
                            cinemaService.deleteCinema(c);
                            response = new ServerResponse(Responses.OKAY, "Cinema deleted.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to delete cinema.", null);
                        }
                        break;
                    case DELETE_HALL:
                        try
                        {
                            Hall h = (Hall)request.getObject();
                            int id = h.getCinemaID();
                            Cinema c = cinemaService.findCinemaEntity(id);
                            c.setCountOfHalls(c.getCountOfHalls() - 1);
                            cinemaService.updateEntity(c);
                            hallService.deleteHall(h);
                            response = new ServerResponse(Responses.OKAY, "Hall deleted.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to delete hall.", null);
                        }
                        break;
                    case SHOW_ALL_MOVIES:
                        try
                        {
                            List<Movie> movies = movieService.findEvery();
                            response = new ServerResponse(Responses.OKAY, "Movies found.", movies);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find movies.", null);
                        }
                        break;
                    case SHOW_TICKETS_FOR_SEANCE:
                        try
                        {
                            int id = (Integer)request.getObject();
                            List<Ticket> tickets = ticketService.findAllForSeance(id);
                            response = new ServerResponse(Responses.OKAY, "found.", tickets);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to find tickets.", null);
                        }
                        break;
                    case ADD_ORDER:
                        try
                        {
                            List<Order> orders = (List<Order>)request.getObject();
                            orderService.placeOrder(orders);

                            boolean isFirst = true;
                            Statistic stat = new Statistic();
                            int norm = 0, comf = 0, VIP = 0;

                            for(Order o: orders)
                            {
                                Ticket t = ticketService.findTicketByID(o.getTicket());
                                if(isFirst) { stat = statisticService.findForSeance(t.getSeanceID()); isFirst = false; }
                                t.setStatus(0);
                                switch (t.getType())
                                {
                                    case 0: { norm++; break; }
                                    case 1: { comf++; break; }
                                    case 2: { VIP++; break; }
                                }
                                ticketService.updateEntity(t);
                            }
                            stat.setStandartTicketsSold(stat.getStandartTicketsSold() + norm);
                            stat.setComfortTicketsSold(stat.getComfortTicketsSold() + comf);
                            stat.setVIPTicketsSold(stat.getVIPTicketsSold() + VIP);
                            statisticService.updateEntity(stat);
                            response = new ServerResponse(Responses.OKAY, "Order placed.", null);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to place order.", null);
                        }
                        break;
                    case SHOW_HALL:
                        try
                        {
                            int id = (Integer)request.getObject();
                            hall = hallService.getHallByID(id);
                            response = new ServerResponse(Responses.OKAY, "Hall shown.", hall);
                        }
                        catch(Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to cshow hall.", null);
                        }
                        break;
                    case VIEW_ORDER_COUNT:
                        try
                        {
                            int id = (Integer)request.getObject();
                            int count = orderService.findAllOrdersFromCertainUser(id).size();
                            response = new ServerResponse(Responses.OKAY, "Order count shown.", count);
                        }
                        catch (Exception e)
                        {
                            response = new ServerResponse(Responses.ERROR, "Server failed to count.", null);
                        }
                        break;
                }
                soos.writeObject(response);
            }
        }
        catch(SocketException e) {  }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " had broken connection;");
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
