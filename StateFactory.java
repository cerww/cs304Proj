import java.util.stream.Stream;

public class StateFactory {
    private StateFactory() {

    }

    @ButtonOption(buttonText = "elections")
    public static DisplayTableSelction elections(Program mainProgram) {
        return
                new DisplayTableSelction(mainProgram,
                        "all_elections ae,election e",
                        "ae.election_id = e.election_id",
                        Stream.of("time", "place","election_name","type")
                )
                .addFilterOptionLike("place")
                .addSelectionFilterOption("type","federal","provincial","municiple");
    }

    @ButtonOption(buttonText = "parties")
    public static DisplayTableSelction politicalParties(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "political_party pp, political_party_years_active ppya",
                "pp.party_name = ppya.party_name and pp.party_name <> \"Independent\" ",
                Stream.of("pp.party_name", "date_established", "years_active")
        ).addFilterOptionLike("pp.party_name")
                .addIntFilterOption("years_active")
                .addDateFilterOption("date_established");
    }

    @ButtonOption(buttonText = "issues/policies")
    public static DisplayTableSelction issues(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "political_issue pi,candidate_positions cp,candidate c,voter v, all_elections e, election ee",
                "pi.issue_name = cp.issue_name and c.voter_id = v.voter_id and cp.candidate_id = c.voter_id and cp.election_id = e.election_id and ee.election_id = e.election_id",
                Stream.of("pi.issue_name", "cp.position", "v.name", "v.age candidate_age", "ee.time date_of_election", "e.place")
                )
                .addIntFilterOption("candidate_age")
                .addFilterOptionLike("pi.issue_name")
                .addFilterOptionLike("cp.position")
                .addFilterOptionLike("e.place")
                .addDateFilterOption("date_of_election");
    }

    @ButtonOption(buttonText = "electoralDistricts")
    public static DisplayTableSelction electoralDistricts(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "electoral_district ed, all_elections e,candidates_elected_in cei,candidate c,voter v,election ee",
                "ed.election_id = e.election_id and cei.district_name = ed.district_name and cei.election_id = e.election_id and c.voter_id = cei.candidate_id and v.voter_id = c.voter_id and ee.election_id = e.election_id",
                Stream.of("ed.district_name","e.place election_place","ee.time election_date","v.name winner_name","v.age winner_age")
                )
                .addFilterOptionLike("ed.district_name")
                .addFilterOptionLike("election_place")
                .addDateFilterOption("election_date")
                .addFilterOptionLike("winner_name")
                .addIntFilterOption("winner_age")
                ;

    }

    @ButtonOption(buttonText = "candidates")
    public static DisplayTableSelction candidates(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "political_party pp, candidate c,voter v,party_member pm,all_elections ae,candidate_ran_in cri,election e",
                "pp.party_name = pm.party_name and c.voter_id = v.voter_id and c.voter_id = pm.member_id and cri.candidate_id = c.voter_id and cri.election_id = ae.election_id and ae.election_id = e.election_id",
                /*attributes*/ Stream.of("v.name","v.age","pp.party_name", "ae.place candidate_in", "e.time election_date")
                )
                .addFilterOptionLike("v.name")
                .addIntFilterOption("v.age")
                .addFilterOptionLike("pp.party_name")
                .addFilterOptionLike("candidate_in")
                .addDateFilterOption("election_date");
    }

    @ButtonOption(buttonText = "majorEvents")
    public static DisplayTableSelction majorEvents(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "major_campaign_event ce, electoral_district ed",
                "ce.election_id = ed.election_id and ce.district_name = ed.district_name",
                Stream.of("ce.location", "ce.time", "ce.party_name","ed.district_name"))
                .addFilterOptionLike("ce.location")
                .addDateFilterOption("ce.time")
                .addFilterOptionLike("ce.party_name");
    }

    @ButtonOption(buttonText = "donations")
    public static DisplayTableSelction donations(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "party_donation_transaction d, transaction_by_voter tv, voter v",
                "d.transaction_id = tv.transaction_id and v.voter_id = tv.voter_id",
                Stream.of("d.amount", "d.party_name", "v.name", "v.age"))
                .addIntFilterOption("d.amount")
                .addFilterOptionLike("d.party_name")
                .addFilterOptionLike("v.name")
                .addIntFilterOption("v.age");
    }

    @ButtonOption(buttonText = "total donations")
    public static DisplayTableSelction total_donations_per_person(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "transaction_by_voter tv,voter v,party_donation_transaction d",
                "tv.voter_id = v.voter_id and tv.transaction_id = d.transaction_id",
                Stream.of("v.name name","v.age age","sum(d.amount) total","d.party_name party_name"),
                "v.voter_id,v.name,d.party_name,v.age")
                .addIntFilterOption("total")
                .addFilterOptionLike("party_name")
                .addFilterOptionLike("v.name")
                .addIntFilterOption("v.age");

    }

    /*
    @ButtonOption(buttonText = "countries")
    public static DisplayTableSelction countries(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "federal_election fe, electoral_district ed, candidates_elected_in cei, candidate c, voter v",
                "fe.election_id = ed.election_id and cei.election_id = ed.election_id and cei.candidate_id = c.voter_id and c.voter_id = v.voter_id",
                Stream.of("fe.country_name", "ed.number_of_votes", "ce.party_name", "v.name"))
                .addFilterOptionLike("fe.country_name")
                .addIntFilterOption("ed.number_of_votes")
                .addFilterOptionLike("ce.party_name")
                .addFilterOptionLike("v.name");
    }
    @ButtonOption(buttonText = "cities")
    public static DisplayTableSelction cities(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "municipal_election me,all_elections ae,election ee",
                "me.election_id = ae.election_id and ae.election_id = ee.e.election_id",
                Stream.of("me.city_name","","","ee.time election_date"))
                .addFilterOptionLike("me.city_name")
                .addIntFilterOption("ed.number_of_votes")
                .addFilterOptionLike("ce.party_name")
                .addFilterOptionLike("v.name");
    }

    @ButtonOption(buttonText = "provinces")
    public static DisplayTableSelction provinces(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "provincial_election pe, electoral_district ed, candidates_elected_in cei, candidate c, voter v",
                "pe.election_id = ed.election_id and cei.election_id = ed.election_id and cei.candidate_id = c.voter_id and c.voter_id = v.voter_id",
                Stream.of("pe.province_name", "ed.number_of_votes", "ce.party_name winning_party", "v.name winner_name"))
                .addFilterOptionLike("pe.province_name")
                .addIntFilterOption("ed.number_of_votes")
                .addFilterOptionLike("ce.party_name winning_party")
                .addFilterOptionLike("v.name winner_name");
    }
    */
    @ButtonOption(buttonText = "seat winnings")
    public static DisplayTableSelction number_of_seats_won(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "electoral_district ed,candidates_elected_in cei,political_party pp,party_member pm,all_elections ae,election ee",
                "ee.election_id = ae.election_id and ae.election_id = ed.election_id and ed.election_id = cei.election_id and cei.candidate_id = pm.member_id and pm.party_name = pp.party_name and cei.district_name = ed.district_name",
                Stream.of("pp.party_name","count(*) seats_won","ae.place","ee.time election_date"),
                "ed.election_id, pp.party_name,ae.place,ee.time").addIntFilterOption("seats_won");
    }

    @ButtonOption(buttonText = "big donaters")
    public static DisplayTableSelction ppl_who_donated_to_everyone(Program mainProgram){
        class TableSelection extends DisplayTableSelction{
            TableSelection(Program mainProgram){
                super(mainProgram,
                        "",
                        "*",
                        Stream.of("name","age"));
            }

            @Override
            public String getStatment(){
                return "Select name, age from voter v where not exists((Select party_name from political_party)" +
                        "except (select party_name from party_donation_transaction d,transaction_by_voter tv " +
                        "where d.transaction_id = tv.transaction_id and tv.voter_id = v.voter_id))";
            }

        }
        return new TableSelection(mainProgram);
    }

    @ButtonOption(buttonText = "modify voters")
    public static Thing modify_voters_thing(Program mainProgram){
        return new ModifyVoterThing(mainProgram);
    }

    @ButtonOption(buttonText = "voters")
    public static DisplayTableSelction voters(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "voter",
                "1",
                Stream.of("voter_id","address","name","age","postal_code")
        ).addIntFilterOption("age").addFilterOptionLike("name");
    }
}

