import java.util.stream.Stream;

public class StateFactory {
    private StateFactory() {

    }

    public static DisplayTableSelction elections(Program mainProgram) {
        return
                new DisplayTableSelction(mainProgram, "all_elections", "1=1",
                        Stream.of("time", "place"))
                .addFilterOptionLike("place");
    }

    public static DisplayTableSelction politicalParties(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "political_party pp, political_party_years_active ppya",
                "pp.party_name = ppya.party_name,pp.party_name <> \"independent\" ",
                Stream.of("party_name", "date_established", "years_active")
        ).addFilterOptionLike("ppya.party_name")
                .addIntFilterOption("years_active")
                .addDateFilterOption("date_established");
    }

    public static DisplayTableSelction issues(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "political_issue pi,candidate_positions cp,candidate c,voters v, all_elections e",
                "pi.issue_name = cp.issue_name and c.voter_id = v.voter_id and cp.candidate_id = c.voter_id, cp.election_id = e.election_id",
                Stream.of("pi.issue_name", "cp.position", "v.name", "v.age candidate_age", "e.date date_of_election", "e.place")
                )
                .addIntFilterOption("candidate_age")
                .addFilterOptionLike("pi.issue_name")
                .addFilterOptionLike("cp.position")
                .addFilterOptionLike("e.place")
                .addDateFilterOption("date_of_election");
    }

    public static DisplayTableSelction electoralDistricts(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "electoral_district ed, all_elections e,candidate_elected_in cei,candidate c,voter v",
                "ed.election_id = e.election_id and cei.election_id = e.election_id and c.voter_id = cei.candidate_id and v.voter_id = c.voter_id",
                Stream.of("ed.district_name","e.place election_place","e.date election_date","v.name winner_name","v.age winner_age")
                )
                .addFilterOptionLike("ed.district_name")
                .addFilterOptionLike("election_place")
                .addDateFilterOption("election_date")
                .addFilterOptionLike("winner_name")
                .addIntFilterOption("winner_age")
                ;

    }

    public static DisplayTableSelction candidates(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "political_party pp, candidate c,voter v,party_member pm,all_elections ae,candidate_ran_in cri",
                "pp.party_name = pm.party_name and c.voter_id = v.voter_id and c.voter_id = pm.member_id and cri.candidate_id = c.voter_id and cri.election_id = ae.election_id",
                /*attributes*/ Stream.of("v.name","v.age","pp.party_name", "ae.place candidate_in", "ae.date election_date")
                )
                .addFilterOptionLike("v.name")
                .addIntFilterOption("v.age")
                .addFilterOptionLike("pp.party_name")
                .addFilterOptionLike("ae.place")
                .addDateFilterOption("ae.date");
    }

    public static DisplayTableSelction majorEvents(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "major_campaign_event ce, electoral_district ed",
                "ce.election_id = ed.election_id",
                Stream.of("ce.location", "ce.time", "ce.party_name"))
                .addFilterOptionLike("ce.location")
                .addDateFilterOption("ce.time")
                .addFilterOptionLike("ce.party_name");
    }

    public static DisplayTableSelction donations(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "party_donation_transaction d, transaction_by_voter tv, voter v",
                "d.transaction_id = tv.transaction_id and v.voter_id = tv.voter_id",
                Stream.of("d.amount", "d.party_name", "v.name", "v.age"))
                .addIntFilterOption("d.amount")
                .addFilterOptionLike("d.party_name")
                .addFilterOptionLike("v.name")
                .addFilterOptionLike("v.age");
    }

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

    public static DisplayTableSelction cities(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "municipal_election me, electoral_district ed, candidates_elected_in cei, candidate c, voter v",
                "me.election_id = ed.election_id and cei.election_id = ed.election_id and cei.candidate_id = c.voter_id and c.voter_id = v.voter_id",
                Stream.of("me.city_name", "ed.number_of_votes", "ce.party_name", "v.name"))
                .addFilterOptionLike("me.city_name")
                .addIntFilterOption("ed.number_of_votes")
                .addFilterOptionLike("ce.party_name")
                .addFilterOptionLike("v.name");
    }

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
}
