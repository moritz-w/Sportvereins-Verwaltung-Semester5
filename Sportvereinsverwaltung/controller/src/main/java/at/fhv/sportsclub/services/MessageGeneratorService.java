package at.fhv.sportsclub.services;

import at.fhv.sportsclub.entity.dept.SportEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class MessageGeneratorService {

    private final String noreply = "";

    public static String informDeptHeadAddMemberToTeam(PersonDTO member, PersonDTO deptHead, SportDTO sport){
        //deptHead muss informiert werden member in ein Team hinzu zu fügen

        StringBuilder sb = new StringBuilder();
        sb.append("Dear " + deptHead.getFirstName() + " " + deptHead.getLastName() + ",");
        sb.append("\n\n");
        sb.append("We musst inform you that new member has entered our club.\n");
        sb.append(member.getFirstName() + " " + member.getLastName() + "must be added to a " + sport.getName() + " team in your department.\n\n");
        sb.append("Kind regards\n");
        return sb.toString();
    }

    public static List<String> informCoachInviteToTurnament(List<PersonDTO> coaches, TournamentDTO tournament){
        //Trainer informieren, dass er am tuniert teil nimmt und einen kader erstellen muss

        StringBuilder sb = new StringBuilder();
        LinkedList<String> messages = new LinkedList<>();
        for (PersonDTO coach :
                coaches) {
            sb.append("Dear " + coach.getFirstName() + " " + coach.getLastName() + ",");
            sb.append("\n\n");
            sb.append("You hav been invited to this tournament: " + tournament.getName());
            sb.append("\nThe tournament will take place on " + tournament.getDate());
            sb.append("\nIf you are the head coach we need you to put together a team which you want to take to this tournament.\n");
            sb.append("Kind regards\n");
            messages.add(sb.toString());
        }
        return messages;
    }

    public static String informPlayerPartOfTeam(TournamentDTO tournament, PersonEntity player, PersonDTO coach){
        //Spieler informieren das sein trainer ihn für das turnier nominiert hat

        StringBuilder sb = new StringBuilder();
        sb.append("Dear " + player.getFirstName() + " " + player.getLastName() + ",");
        sb.append("\n\n");
        sb.append("Congratulations!\n" + "You have been nominated for this tournament: " + tournament.getName() + ".\n");
        sb.append("The tournament will take place on " + tournament.getDate());
        sb.append("\nI need you to give me a response weather you are able to take part or not.\n\n");
        sb.append("Kind regards\n" + coach.getFirstName() + " " + coach.getLastName());


        return sb.toString();
    }

    public static String informCoachIfPlayerTakesPartOrNot(String sentMessage, Boolean confirm, PersonDTO player){
        //Der spieler muss dem trainer bescheid geben ob er am tunier teilnemen wird oder nicht
        StringBuilder sb = new StringBuilder();

        sb.append("This is the message you sent:\n\n");
        sb.append(sentMessage);
        sb.append("\n\n");
        if (confirm.booleanValue()){
            sb.append(player.getFirstName() + player.getLastName() + "will be able to take part in this event");
        } else {
            sb.append("Unfortunately " + player.getFirstName() + player.getLastName() + "will not be able to take part in this event");
        }
        sb.append("\n\nKind regards");

        return sb.toString();
    }

}
