import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Function;

//import java.util.concurrent.Flow;


public class TopMenu extends JPanel {
    private Program mainProgram;
    JButton electionsButton;
    JButton countriesButton;
    JButton citiesButton;
    JButton provincesButton;
    JButton electoralDistrictsButton;
    JButton candidatesButton;
    JButton donationsButton;
    JButton partiesButton;
    JButton majorEventsButton;
    JButton politicalIssueButton;


    public TopMenu(Program pm) {
        setLayout(new FlowLayout());
        mainProgram = pm;
        electionsButton = new JButton("elections");
        countriesButton = new JButton("countries");
        citiesButton = new JButton("cities");
        provincesButton = new JButton("provinces");
        electoralDistrictsButton = new JButton("electoral districts");
        candidatesButton = new JButton("candidates");
        donationsButton = new JButton("donations");
        partiesButton = new JButton("parties");
        majorEventsButton = new JButton("events");
        politicalIssueButton = new JButton("issues/policies");

        add(electionsButton);
        add(majorEventsButton);
        add(countriesButton);
        add(citiesButton);
        add(provincesButton);
        add(electoralDistrictsButton);
        add(candidatesButton);
        add(partiesButton);
        add(donationsButton);
        add(politicalIssueButton);


        electionsButton.addActionListener(actionListenerFor(StateFactory::elections));
        majorEventsButton.addActionListener(actionListenerFor(StateFactory::majorEvents));
        //countries
        //cities
        //provinces
        electoralDistrictsButton.addActionListener(actionListenerFor(StateFactory::electoralDistricts));
        partiesButton.addActionListener(actionListenerFor(StateFactory::politicalParties));
        politicalIssueButton.addActionListener(actionListenerFor(StateFactory::issues));
        candidatesButton.addActionListener(actionListenerFor(StateFactory::candidates));
        donationsButton.addActionListener(actionListenerFor(StateFactory::donations));
    }

    private ActionListener actionListenerFor(Function<Program,DisplayTableSelction> f){
        return (e)->mainProgram.changeState(f.apply(mainProgram));
    }

}
//elections, countries, municiples, provinces, policies, candidates ,electoral districts, donations
//parties, leaders, campiegn events