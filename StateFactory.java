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
        ).addFilterOptionLike("ppya.party_name");
    }

    public static DisplayTableSelction issues(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "political_issue pi,candidate_positions cp,candidate c,voters v, all_elections e",
                "pi.issue_name = cp.issue_name and c.voter_id = v.voter_id and cp.candidate_id = c.voter_id, cp.election_id = e.election_id",
                Stream.of("pi.issue_name", "cp.position", "v.name", "v.age", "e.date", "e.place")
                )
                .addIntFilterOption("v.age")
                .addFilterOptionLike("pi.issue_name")
                .addFilterOptionLike("cp.position")
                .addFilterOptionLike("e.place").addDateFilterOption("e.date");
    }

    public static DisplayTableSelction electoralDistricts(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "electoral_district ed, all_elections e,candidate_elected_in cei,candidate c,voter v",
                "ed.election_id = e.election_id and cei.election_id = e.election_id and c.voter_id = cei.candidate_id and v.voter_id = c.voter_id",
                Stream.of("ed.district_name","e.place","e.date","v.name","v.age")
                )
                .addFilterOptionLike("ed.district_name")
                .addFilterOptionLike("e.place")
                .addFilterOptionLike("v.name")
                .addIntFilterOption("v.age").addDateFilterOption("e.date");
    }

    public static DisplayTableSelction candidates(Program mainProgram){
        return new DisplayTableSelction(mainProgram,
                "political_party pp, candidate c,voter v,party_member pm,all_elections ae,candidate_ran_in cri",
                "pp.party_name = pm.party_name and c.voter_id = v.voter_id and c.voter_id = pm.member_id and cri.candidate_id = c.voter_id and cri.election_id = ae.election_id",
                Stream.of("v.name","v.age","pp.party_name", "ae.place", "ae.date")
        ).addFilterOptionLike("v.name")
                .addIntFilterOption("v.age").addFilterOptionLike("pp.party_name").addFilterOptionLike("ae.place").addDateFilterOption("ae.date");
    }
    public static DisplayTableSelction majorEvents(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "major_campaign_event ce, electoral_district ed",
                "ce.election_id = ed.election_id",
                Stream.of("ce.location", "ce.time", "ce.party_name")).addFilterOptionLike("ce.location").addDateFilterOption("ce.time");
    }
    public static DisplayTableSelction donations(Program mainProgram) {
        return new DisplayTableSelction(mainProgram,
                "party_donation_transaction d, transaction_by_voter tv, voter v",
                "d.trasaction_id = tv.transaction_id and v.voter_id = tv.voter_id",
                Stream.of("d.amount", "d.party_name", "v.name", "v.age"))
                .addIntFilterOption("d.amount")
                .addFilterOptionLike("d.party_name")
                .addFilterOptionLike("v.name")
                .addFilterOptionLike("v.age");
    }
    // countries
    // cities
    // provinces
}
