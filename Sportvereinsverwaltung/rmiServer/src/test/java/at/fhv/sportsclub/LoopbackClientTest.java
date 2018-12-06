package at.fhv.sportsclub;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.AddressDTO;
import at.fhv.sportsclub.model.person.ContactDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.SquadMemberDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.sportsclub.security.authentication.IAuthenticationController;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;

import at.fhv.sportsclub.starter.ServerRunMe;
import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.factory.IControllerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class LoopbackClientTest {

    private static int port = 1099;
    private static Registry registry;
    private static IAuthenticationController authController;
    private static IControllerFactory controllerFactory;
    private static SessionDTO session;

    @BeforeClass
    public static void startServer(){
        try {
            ServerRunMe.createRMIRegistry(port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            registry = LocateRegistry.getRegistry(port);
            authController = (IAuthenticationController) registry.lookup("AuthenticationService");
            controllerFactory = (IControllerFactory) registry.lookup("ControllerFactory");
            session = authController.authenticate("snoop@do.gg", "snoop@do.gg".toCharArray());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTournamentByTrainer() throws RemoteException, NotBoundException{
        ITournamentController tournamentController = controllerFactory.getTournamentController();
        ListWrapper<TournamentDTO> tournamentByTrainerId = tournamentController.getTournamentByTrainerId(session, "5c084d41b360dbb8213877c7");
    }

    @Test
    public void testGetTeamsBySport() throws RemoteException, NotBoundException {
        ITeamController teamController = controllerFactory.getTeamController();
        ListWrapper<TeamDTO> bySport = teamController.getBySport(session, "5c084d41b360dbb82138781a");
    }

    @Test
    public void testGetTournaments() throws RemoteException, NotBoundException {
        ITournamentController tournamentController = controllerFactory.getTournamentController();
        ListWrapper<TournamentDTO> allEntries = tournamentController.getAllEntries(session);
        TournamentDTO entryDetails = tournamentController.getEntryDetails(session, allEntries.getContents().get(0).getId());
    }

    @Test
    public void testRMIConnectionAndLookupFactory() throws RemoteException, NotBoundException {
        ITeamController teamController = controllerFactory.getTeamController();
        ITournamentController controller = controllerFactory.getTournamentController();
        IPersonController personController = controllerFactory.getPersonController();
        IDepartmentController departmentController = controllerFactory.getDepartmentController();

        DepartmentDTO departmentBySportId = departmentController.getDepartmentBySportId(session, "5c05eb82e9a0326156979fcb");

        PersonDTO person = personController.getEntryDetails(session, "5bf4b57197710c31da9f3253");

        ListWrapper<TournamentDTO> tournamentByTrainerId1 = controller.getTournamentByTrainerId(session, session.getMyUserId());

        TeamDTO entryDetails = teamController.getEntryDetails(session, "5c05eb83e9a032615697a00c");

        ListWrapper<TeamDTO> teamsByTrainerId = teamController.getTeamsByTrainerId(session, "5bf4b57197710c31da9f3266");
        ListWrapper<TournamentDTO> tournamentByTrainerId = controller.getTournamentByTrainerId(session, "5bf4b57197710c31da9f3266");
        ListWrapper<TeamDTO> bySport = teamController.getBySport(session, "5bf4b57197710c31da9f3249");
        ListWrapper<TeamDTO> byLeague = teamController.getByLeague(session, "5bf4b57197710c31da9f323d");

        TournamentDTO tournamentDTO = new TournamentDTO();
        List<ParticipantDTO> teams = new ArrayList<>();
        ParticipantDTO teamA = new ParticipantDTO();
        teamA.setId("5c045201cbb3a266a8df2e4c");
        teamA.setTeam("5bf4b57197710c31da9f328a");
        teamA.setModificationType(ModificationType.MODIFIED);
        ArrayList<SquadMemberDTO> personDTOS = new ArrayList<>();
        //personDTOS.add(person);
        teamA.setParticipants(personDTOS);
        teams.add(teamA);
//        tournamentDTO.setLeague("5bf4b57197710c31da9f3248");
        tournamentDTO.setTeams(teams);
        List<EncounterDTO> encounters = new ArrayList<>();
        EncounterDTO exampleEncounter = new EncounterDTO();
        exampleEncounter.setId("5c0454b4cbb3a283a41d00d3");
        exampleEncounter.setHomePoints(500);
        exampleEncounter.setHomeTeam("5c045201cbb3a266a8df2e4c");
        exampleEncounter.setGuestTeam("5c045201cbb3a266a8df2e4c");
        exampleEncounter.setModificationType(ModificationType.REMOVED);
        encounters.add(exampleEncounter);
        tournamentDTO.setEncounters(encounters);
        tournamentDTO.setId("5c04455ecbb3a26f4cb9ec5b");
        //tournamentDTO.setModificationType(ModificationType.MODIFIED);
        TournamentDTO tournament = controller.saveOrUpdateEntry(session, tournamentDTO);


        //ArrayList<PersonDTO> allEntries = personController.getAllEntries(null);
        //IAuthenticationController authenticationController = controllerFactory.getAuthenticationController();
        //String pw = "test";
        //SessionDTO session = authenticationController.authenticate("mwi4339@students.fhv.at", pw.toCharArray());

//        IDepartmentController departmentController = controllerFactory.getDepartmentController();
//        SportDTO randomSport = departmentController.getAllSportEntries().get(0);
//        AddressDTO address = new AddressDTO(null, "Memory lane", "000", "Compton", null);
//        ContactDTO contact = new ContactDTO(null, "0009", "mw@gmail.com", null);
//        ArrayList<SportDTO> sports = new ArrayList<>();
//        sports.add(randomSport);
//        ResponseMessageDTO responseMessageDTO = personController.saveOrUpdateEntry(
//                new PersonDTO(null, "Alfons", "Hatler", LocalDate.now(), address, contact, sports, null, null)
//        );
        //PersonDTO entryDetails = personController.getEntryDetails(responseMessageDTO.getContextId());

        //ArrayList<PersonDTO> allEntries = personController.getAllEntries(null);
        //PersonDTO snoopDogg = personController.getEntryDetails(allEntries.get(0).getId());
       // System.out.println(snoopDogg.toString());
    }


}
